package com.bunsaned3thinking.mogu.post.service.module;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.common.config.S3Config;
import com.bunsaned3thinking.mogu.common.util.LocationUtil;
import com.bunsaned3thinking.mogu.common.util.UpdateUtil;
import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.SearchHistoryResponse;
import com.bunsaned3thinking.mogu.post.entity.Category;
import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.HiddenPostId;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostDetailImage;
import com.bunsaned3thinking.mogu.post.entity.PostDoc;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.post.repository.component.PostComponentRepository;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
	private static final int DEFAULT_PAGE__SIZE = 10;

	private final PostComponentRepository postComponentRepository;

	@Override
	public ResponseEntity<PostWithDetailResponse> createPost(String userId, PostRequest postRequest,
		List<String> postImageLinks) {
		validatePostRequest(postRequest);
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Post post = postRequest.toEntity(user);
		postComponentRepository.savePost(post);
		PostDetail postDetail = postRequest.toDetailEntity(post);
		postComponentRepository.savePostDetail(postDetail);
		List<PostImage> postImages = createPostImages(postImageLinks);
		postComponentRepository.saveAllPostImages(postImages);
		List<PostDetailImage> postDetailImages = postImages.stream()
			.map(postImage -> PostDetailImage.of(postDetail, postImage))
			.collect(Collectors.toList());
		postDetail.updatePostImages(postDetailImages);

		int pricePerCount;
		if (postRequest.getShareCondition()) {
			pricePerCount = (int)Math.ceil((double)postRequest.getDiscountPrice() / postRequest.getUserCount());
		} else {
			pricePerCount = postRequest.getPricePerCount();
		}

		post.initialize(postDetail, postImages.get(0), pricePerCount);
		postComponentRepository.savePostDoc(PostDoc.from(post));
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostWithDetailResponse.from(post));
	}

	private List<PostImage> createPostImages(List<String> postImageLinks) {
		if (!postImageLinks.isEmpty()) {
			return postImageLinks.stream()
				.map(PostImage::from)
				.collect(Collectors.toList());
		}
		List<PostImage> postImages = new ArrayList<>();
		postComponentRepository.findPostImageByPostImageId(S3Config.PostImageId)
			.ifPresentOrElse(postImages::add, () -> {
				throw new EntityNotFoundException("[Error] 기본 프로필 이미지를 가져오는데 실패했습니다.");
			});
		return postImages;
	}

	@Override
	public ResponseEntity<PostResponse> findPost(Long id) {
		Post post = postComponentRepository.findPostById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (post.getPostDetail() == null) {
			throw new IllegalArgumentException("[Error] 삭제된 게시글은 조회할 수 없습니다");
		}
		if (post.getIsHidden()) {
			throw new IllegalArgumentException("[Error] 숨겨진 게시글은 조회할 수 없습니다.");
		}
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(post));
	}

	@Override
	public ResponseEntity<PostWithDetailResponse> findPostWithDetail(Long id) {
		Post post = postComponentRepository.findPostById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (post.getPostDetail() == null) {
			throw new IllegalArgumentException("[Error] 삭제된 게시글은 조회할 수 없습니다");
		}
		if (post.getIsHidden()) {
			throw new IllegalArgumentException("[Error] 숨겨진 게시글은 조회할 수 없습니다.");
		}
		post.addViewCount();
		Post savedPost = postComponentRepository.savePost(post);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostWithDetailResponse.from(savedPost));
	}

	@Override
	public ResponseEntity<PostWithDetailResponse> updatePost(String userId, Long postId,
		UpdatePostRequest updatePostRequest, List<String> postImageLinks) {
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 수정할 수 있습니다.");
		}
		if (post.getPostDetail() == null) {
			throw new IllegalArgumentException("[Error] 삭제된 게시글은 수정할 수 없습니다.");
		}
		if (post.getRecruitState().equals(RecruitState.CLOSING)) {
			throw new IllegalArgumentException("[Error] 마감된 게시글은 수정할 수 없습니다.");
		}
		validatePostRequest(post, updatePostRequest);
		UpdatePostRequest originPost = UpdatePostRequest.from(post);
		List<PostDetailImage> postDetailImages = post.getPostDetail().getPostImages();
		List<PostImage> postImages = postDetailImages.stream()
			.map(PostDetailImage::getPostImage)
			.collect(Collectors.toList());
		postComponentRepository.deletePostDetailImages(postDetailImages);
		if (postImages.get(0).getId() != S3Config.PostImageId) {
			postComponentRepository.deletePostImages(postImages);
		}
		postImages = createPostImages(postImageLinks);
		if (!postImageLinks.isEmpty()) {
			postComponentRepository.saveAllPostImages(postImages);
		}
		post.updateThumbnail(postImages.get(0));
		PostDetail postDetail = post.getPostDetail();
		postDetailImages = postImages.stream()
			.map(postImage -> PostDetailImage.of(postDetail, postImage))
			.collect(Collectors.toList());
		postDetail.updatePostImages(postDetailImages);
		postComponentRepository.savePostDetail(postDetail);
		UpdateUtil.copyNonNullProperties(updatePostRequest, originPost);
		update(post, originPost);
		Post updatedPost = postComponentRepository.savePost(post);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostWithDetailResponse.from(updatedPost));
	}

	@Override
	public ResponseEntity<List<PostResponse>> findAllReportedPost(Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, DEFAULT_PAGE__SIZE);
		if (cursor == 0) {
			return findReportedPostFirstPage(pageRequest);
		}
		Post post = postComponentRepository.findPostById(cursor)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		System.out.println(cursor + " " + post.getReports().size());
		Slice<Post> reportedPosts = postComponentRepository.findAllReportedPost(post.getReports().size(), cursor,
			pageRequest);
		List<PostResponse> responses = reportedPosts.stream()
			.peek(post1 -> System.out.println(post1.getId()))
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	private ResponseEntity<List<PostResponse>> findReportedPostFirstPage(PageRequest pageRequest) {
		Slice<Post> reportedPosts = postComponentRepository.findAllFirstPageReportedPost(pageRequest);
		List<PostResponse> responses = reportedPosts.stream()
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	@Override
	public ResponseEntity<List<SearchHistoryResponse>> findAllSearchHistoryByUserId(String userId) {
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		List<SearchHistory> searchHistories = user.getSearchHistories();
		List<SearchHistoryResponse> searchHistoryResponses = searchHistories
			.stream()
			.map(SearchHistoryResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(searchHistoryResponses);
	}

	@Override
	public ResponseEntity<List<PostResponse>> searchPostsByKeyword(String keyword, String userId, Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, DEFAULT_PAGE__SIZE);
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		user.getSearchHistories().add(postComponentRepository.saveSearchHistory(keyword, user));

		Slice<Post> posts;
		if (cursor == 0) {
			posts = postComponentRepository.searchFirstPostsByKeyword(user.getUid(), user.getLocation(),
				user.getDistanceMeters(), keyword, pageRequest);
		} else {
			posts = postComponentRepository.searchPostsByKeyword(user.getUid(), user.getLocation(),
				user.getDistanceMeters(), keyword, cursor, pageRequest);
		}

		List<PostResponse> responses = posts.stream()
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	private void update(Post post, UpdatePostRequest updatePostRequest) {
		Category category = post.getCategory();
		LocalDate purchaseDate = updatePostRequest.getPurchaseDate();
		int userCount = updatePostRequest.getUserCount();
		String title = updatePostRequest.getTitle();
		int discountCost = updatePostRequest.getDiscountPrice();
		int originalCost = updatePostRequest.getOriginalPrice();
		boolean shareCondition = updatePostRequest.getShareCondition();
		int pricePerCount;
		if (updatePostRequest.getShareCondition()) {
			pricePerCount = (int)Math.ceil(
				(double)updatePostRequest.getDiscountPrice() / updatePostRequest.getUserCount());
		} else {
			pricePerCount = updatePostRequest.getPricePerCount();
		}
		Point location = LocationUtil.createPoint(updatePostRequest.getLongitude(),
			updatePostRequest.getLatitude());
		post.update(category, purchaseDate, userCount, title, discountCost, originalCost, shareCondition,
			pricePerCount, location);

		String content = updatePostRequest.getContent();
		post.getPostDetail().update(content);
	}

	@Override
	public List<String> deletePost(String userId, Long postId) {
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 삭제할 수 있습니다.");
		}
		if (post.getPostDetail() == null) {
			throw new IllegalArgumentException("[Error] 이미 삭제된 게시글입니다.");
		}
		List<PostDetailImage> postDetailImages = post.getPostDetail().getPostImages();
		List<PostImage> postImages = postDetailImages.stream()
			.map(PostDetailImage::getPostImage)
			.collect(Collectors.toList());
		List<String> imageNames = postImages.stream()
			.map(PostImage::getImage)
			.collect(Collectors.toList());
		if (post.getThumbnail().getId() != S3Config.PostImageId) {
			post.updateThumbnail(postComponentRepository.findPostImageByPostImageId(1)
				.orElseThrow(() -> new EntityNotFoundException("[Error] 기본 게시글 이미지 접근에 실패했습니다.")));
		}
		post.deleteDetail();
		postComponentRepository.deletePostDetailImages(postDetailImages);
		postComponentRepository.deletePostDetailByPostId(postId);
		postComponentRepository.savePost(post);
		if (postImages.get(0).getId() == S3Config.PostImageId) {
			return new ArrayList<>();
		}
		postComponentRepository.deletePostImages(postImages);
		return imageNames;
	}

	@Override
	public ResponseEntity<Void> deleteSearchHistory(Long searchHistoryId, String userId) {
		SearchHistory searchHistory = postComponentRepository.findSearchHistoryById(searchHistoryId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 검색기록을 찾을 수 없습니다."));
		if (!searchHistory.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신이 검색한 기록만 삭제할 수 있습니다.");
		}
		postComponentRepository.deleteSearchHistoryById(searchHistoryId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@Override
	public ResponseEntity<PostResponse> closePost(Long postId, String userId, RecruitState recruitState) {
		if (recruitState.equals(RecruitState.RECRUITING)) {
			throw new IllegalArgumentException("[Error] 모집 중 상태로는 변경할 수 없습니다.");
		}
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 종료할 수 있습니다.");
		}
		if (post.getRecruitState().equals(RecruitState.CLOSING) | post.getRecruitState()
			.equals(RecruitState.PURCHASED)) {
			throw new IllegalArgumentException("[Error] 이미 종료된 게시글입니다.");
		}
		post.updateRecruitState(recruitState);
		Post savedPost = postComponentRepository.savePost(post);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(savedPost));
	}

	@Override
	public ResponseEntity<PostResponse> hidePost(Long postId, String userId, boolean state) {
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		if (post.getUser().equals(user)) {
			return hideMyPost(post, state);
		}
		return hideOtherPost(post, user, state);
	}

	@Override
	public ResponseEntity<List<PostResponse>> findLikedPostsByUserId(String userId, Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, DEFAULT_PAGE__SIZE);
		Slice<Post> posts = getAllLikedPosts(userId, cursor, pageRequest);
		List<PostResponse> responses = posts.stream()
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	private Slice<Post> getAllLikedPosts(String userId, Long cursor, PageRequest pageRequest) {
		if (cursor == 0) {
			return postComponentRepository.findLikedPostsFirstPageByUserId(userId, pageRequest);
		}
		return postComponentRepository.findLikedPostsByUserId(userId, cursor, pageRequest);
	}

	private ResponseEntity<PostResponse> hideMyPost(Post post, boolean state) {
		if (post.getIsHidden() == state) {
			throw new IllegalArgumentException("[Error] 해당 상태의 게시글입니다.");
		}
		post.updateHidden(state);
		Post savedPost = postComponentRepository.savePost(post);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(savedPost));
	}

	private ResponseEntity<PostResponse> hideOtherPost(Post post, User user, boolean state) {
		if (post.getIsHidden()) {
			throw new IllegalArgumentException("[Error] 이미 작성자가 숨긴 게시글입니다.");
		}
		HiddenPostId hiddenPostId = HiddenPostId.of(user.getUid(), post.getId());
		boolean isExistHiddenPost = postComponentRepository.existsHiddenPostById(hiddenPostId);
		if (state == isExistHiddenPost) {
			throw new IllegalArgumentException("[Error] 해당 상태의 게시글입니다.");
		}
		if (state) {
			HiddenPost hiddenPost = HiddenPost.of(user, post);
			postComponentRepository.saveHiddenPost(hiddenPost);
		} else {
			postComponentRepository.deleteHiddenPostById(hiddenPostId);
		}
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(post));
	}

	@Override
	public ResponseEntity<List<PostResponse>> findAllPost(String userId, Long cursor) {
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		PageRequest pageRequest = PageRequest.of(0, DEFAULT_PAGE__SIZE);
		Slice<Post> posts = getAllPosts(pageRequest, cursor, user.getUid(), user.getLocation(),
			user.getDistanceMeters());
		List<PostResponse> responses = posts.stream()
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	private Slice<Post> getAllPosts(PageRequest pageRequest, Long cursor, Long userUid, Point referencePoint,
		Short distanceMeters) {
		if (cursor == 0) {
			return postComponentRepository.findFirstPagePosts(userUid, pageRequest, referencePoint, distanceMeters);
		}
		return postComponentRepository.findNextPagePosts(userUid, cursor, pageRequest, referencePoint, distanceMeters);
	}

	private void validatePostRequest(Post post, UpdatePostRequest postRequest) {
		int originalPrice =
			postRequest.getOriginalPrice() != null ? postRequest.getOriginalPrice() : post.getOriginalPrice();
		int discountPrice =
			postRequest.getDiscountPrice() != null ? postRequest.getDiscountPrice() : post.getDiscountPrice();
		if (originalPrice <= discountPrice) {
			throw new IllegalArgumentException("할인 가격은 기존 가격보다 낮아야됩니다.");
		}
		if (Boolean.FALSE.equals(postRequest.getShareCondition()) & postRequest.getPricePerCount() == null) {
			throw new IllegalArgumentException("개수 당 가격을 입력해주세요.");
		}
		boolean shareCondition =
			postRequest.getShareCondition() != null ? postRequest.getShareCondition() : post.getShareCondition();
		if (shareCondition & postRequest.getPricePerCount() != null) {
			throw new IllegalArgumentException("균등 분배 상태에서는 개수 당 가격을 지정할 수 없습니다.");
		}
	}

	private void validatePostRequest(PostRequest postRequest) {
		if (!postRequest.getShareCondition() & postRequest.getPricePerCount() == null) {
			throw new IllegalArgumentException("개수 당 가격을 입력해주세요.");
		}
		if (postRequest.getOriginalPrice() <= postRequest.getDiscountPrice()) {
			throw new IllegalArgumentException("할인 가격은 기존 가격보다 낮아야됩니다.");
		}
	}

	@Scheduled(cron = "0 3 0 * * *", zone = "Asia/Seoul") // 매일 0시 3분에 실행
	public void autoClosePost() {
		LocalDate purchaseDate = LocalDate.now().minusDays(1);
		List<Post> posts = postComponentRepository.findAllPostsByPurchaseDate(purchaseDate);
		posts.forEach(post -> post.updateRecruitState(RecruitState.CLOSING));
		postComponentRepository.saveAllPosts(posts);
	}
}
