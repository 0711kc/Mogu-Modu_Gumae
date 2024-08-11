package com.bunsaned3thinking.mogu.post.service.module;

import java.time.LocalDate;
import java.util.List;

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
import com.bunsaned3thinking.mogu.common.exception.DeletedPostException;
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
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.post.repository.component.PostComponentRepository;
import com.bunsaned3thinking.mogu.report.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.report.dto.response.ReportResponse;
import com.bunsaned3thinking.mogu.report.entity.Report;
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
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		PostDetail postDetail = postRequest.toDetailEntity();
		List<PostImage> postImages = createPostImages(postImageLinks, postDetail);
		postComponentRepository.saveAllPostImages(postImages);
		postDetail.updatePostImages(postImages);
		Post post = postRequest.toEntity(user, postDetail);
		postDetail.initialize(post);
		postComponentRepository.savePostDetail(postDetail);
		Post savedPost = postComponentRepository.savePost(post);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostWithDetailResponse.from(savedPost));
	}

	private List<PostImage> createPostImages(List<String> postImageLinks, PostDetail postDetail) {
		if (postImageLinks.isEmpty()) {
			return List.of(PostImage.from(S3Config.PostImage, postDetail));
		}
		return postImageLinks.stream()
			.map(postImageLink -> PostImage.from(postImageLink, postDetail))
			.toList();
	}

	@Override
	public ResponseEntity<ReportResponse> createReport(Long postId, String userId, ReportRequest reportRequest) {
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시물을 찾을 수 없습니다."));
		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		if (post.getUser().equals(user)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글은 신고할 수 없습니다.");
		}
		boolean isReportExists = postComponentRepository.isReportExists(post.getId(), user.getUid());
		if (isReportExists) {
			throw new IllegalArgumentException("[Error] 게시물 신고는 한 번만 가능합니다.");
		}
		Report report = reportRequest.toEntity(post, user);
		Report savedReport = postComponentRepository.saveReport(report);
		post.getReports().add(savedReport);
		user.getReports().add(savedReport);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ReportResponse.from(savedReport));
	}

	@Override
	public ResponseEntity<PostResponse> findPost(Long id) {
		Post post = postComponentRepository.findPostById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
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
		UpdatePostRequest originPost;
		try {
			originPost = UpdatePostRequest.from(post);
		} catch (DeletedPostException e) {
			throw new IllegalArgumentException("[Error] 삭제된 게시글은 수정할 수 없습니다.");
		}
		if (post.getRecruitState().equals(RecruitState.CLOSING)) {
			throw new IllegalArgumentException("[Error] 마감된 게시글은 수정할 수 없습니다.");
		}
		List<PostImage> postImages = post.getPostDetail().getPostImages();
		if (!postImageLinks.isEmpty()) {
			postImages = createPostImages(postImageLinks, post.getPostDetail());
		}
		UpdateUtil.copyNonNullProperties(updatePostRequest, originPost);
		update(post, originPost, postImages);
		postComponentRepository.savePostDetail(post.getPostDetail());
		Post updatedPost = postComponentRepository.savePost(post);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostWithDetailResponse.from(updatedPost));
	}

	@Override
	public ResponseEntity<List<PostResponse>> findAllReportedPost(Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, DEFAULT_PAGE__SIZE);
		Slice<Post> reportedPosts = postComponentRepository.findAllReportedPost(cursor, pageRequest);
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
	public ResponseEntity<List<PostResponse>> searchPostsByTitle(String keyword, String userId, Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, DEFAULT_PAGE__SIZE);
		Slice<Post> posts = postComponentRepository.searchPostsByTitle(keyword, cursor, pageRequest);

		User user = postComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		user.getSearchHistories().add(postComponentRepository.saveSearchHistory(keyword, user));

		List<PostResponse> responses = posts.stream()
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
	}

	private void update(Post post, UpdatePostRequest updatePostRequest, List<PostImage> postImages) {
		Category category = post.getCategory();
		LocalDate purchaseDate = updatePostRequest.getPurchaseDate();
		int userCount = updatePostRequest.getUserCount();
		String title = updatePostRequest.getTitle();
		int discountCost = updatePostRequest.getDiscountCost();
		int originalCost = updatePostRequest.getOriginalCost();
		Point location = LocationUtil.createPoint(updatePostRequest.getLongitude(),
			updatePostRequest.getLatitude());
		post.update(category, purchaseDate, userCount, title, discountCost, originalCost, location);

		String content = updatePostRequest.getContent();
		boolean shareCondition = updatePostRequest.getShareCondition();
		post.getPostDetail().update(content, shareCondition);
		post.getPostDetail().updatePostImages(postImages);
	}

	@Override
	public ResponseEntity<Void> deletePost(String userId, Long postId) {
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 삭제할 수 있습니다.");
		}
		post.updateHidden(true);
		postComponentRepository.savePost(post);
		postComponentRepository.deletePostDetailByPostId(post.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
	public ResponseEntity<PostResponse> closePost(Long postId, String userId) {
		Post post = postComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 마감할 수 있습니다.");
		}
		if (post.getRecruitState().equals(RecruitState.CLOSING)) {
			throw new IllegalArgumentException("[Error] 이미 마감된 게시글입니다.");
		}
		post.updateRecruitState(RecruitState.CLOSING);
		// TODO 거래에 참여한 사용자의 레벨 추가
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
		List<Post> posts = postComponentRepository.findLikedPostsByUserId(userId, cursor, pageRequest);
		List<PostResponse> responses = posts.stream()
			.map(PostResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(responses);
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
		return postComponentRepository.findNextPagePosts(userUid, cursor, pageRequest, referencePoint, distanceMeters);
	}

	@Scheduled(cron = "0 3 0 * * *", zone = "Asia/Seoul") // 매일 0시 3분에 실행
	public void autoClosePost() {
		LocalDate purchaseDate = LocalDate.now().minusDays(1);
		List<Post> posts = postComponentRepository.findAllPostsByPurchaseDate(purchaseDate);
		posts.forEach(post -> post.updateRecruitState(RecruitState.CLOSING));
		postComponentRepository.saveAllPosts(posts);
	}
}
