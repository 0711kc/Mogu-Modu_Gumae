package com.bunsaned3thinking.mogu.post.repository.component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.HiddenPostId;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostDoc;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.repository.module.HiddenPostRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostDetailRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostImageRepository;
import com.bunsaned3thinking.mogu.post.repository.module.elasticsearch.PostDocElasticRepository;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;
import com.bunsaned3thinking.mogu.searchhistory.repository.SearchHistoryRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostComponentRepositoryImpl implements PostComponentRepository {
	private final PostDocElasticRepository postDocElasticRepository;
	private final UserRepository userRepository;
	private final PostDetailRepository postDetailRepository;
	private final PostImageRepository postImageRepository;
	private final PostJpaRepository postJpaRepository;
	private final SearchHistoryRepository searchHistoryRepository;
	private final HiddenPostRepository hiddenPostRepository;

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public void saveAllPostImages(List<PostImage> postImages) {
		postImageRepository.saveAll(postImages);
	}

	@Override
	public void savePostDetail(PostDetail postDetail) {
		postDetailRepository.save(postDetail);
	}

	@Override
	public Post savePost(Post post) {
		Post savedPost = postJpaRepository.save(post);
		postDocElasticRepository.save(
			PostDoc.from(savedPost));
		return savedPost;
	}

	@Override
	public SearchHistory saveSearchHistory(String keyword, User user) {
		return searchHistoryRepository.save(SearchHistory.of(keyword, user));
	}

	@Override
	public Optional<Post> findPostById(Long id) {
		return postJpaRepository.findById(id);
	}

	@Override
	public Optional<SearchHistory> findSearchHistoryById(Long searchHistoryId) {
		return searchHistoryRepository.findById(searchHistoryId);
	}

	@Override
	public void deletePostDetailByPostId(Long id) {
		postDetailRepository.deleteByPostId(id);
	}

	@Override
	public void deleteSearchHistoryById(Long searchHistoryId) {
		searchHistoryRepository.deleteById(searchHistoryId);
	}

	@Override
	public Slice<Post> findAllFirstPageReportedPost(PageRequest pageRequest) {
		return postJpaRepository.findReportedPostFirstPage(pageRequest);
	}

	@Override
	public Slice<Post> findFirstPagePosts(Long userUid, PageRequest pageRequest, Point referencePoint,
		Short distanceMeters) {
		return postJpaRepository.findFirstPage(userUid, referencePoint, distanceMeters, pageRequest);
	}

	@Override
	public List<Post> findAllPostsByPurchaseDate(LocalDate purchaseDate) {
		return postJpaRepository.findByPurchaseDate(purchaseDate);
	}

	@Override
	public void saveAllPosts(List<Post> posts) {
		postJpaRepository.saveAll(posts);
	}

	@Override
	public Slice<Post> findNextPagePosts(Long userUid, Long cursor, PageRequest pageRequest,
		Point referencePoint, Short distanceMeters) {
		return postJpaRepository.findNextPage(userUid, referencePoint, distanceMeters, cursor, pageRequest);
	}

	@Override
	public void saveHiddenPost(HiddenPost hiddenPost) {
		hiddenPostRepository.save(hiddenPost);
	}

	@Override
	public void deleteHiddenPostById(HiddenPostId hiddenPostId) {
		hiddenPostRepository.deleteById(hiddenPostId);
	}

	@Override
	public boolean existsHiddenPostById(HiddenPostId hiddenPostId) {
		return hiddenPostRepository.existsById(hiddenPostId);
	}

	@Override
	public Slice<Post> findLikedPostsByUserId(String userId, Long cursor, PageRequest pageRequest) {
		return postJpaRepository.findLikedPostPage(cursor, userId, pageRequest);
	}

	@Override
	public Slice<Post> findLikedPostsFirstPageByUserId(String userId, PageRequest pageRequest) {
		return postJpaRepository.findLikedPostFirstPage(userId, pageRequest);
	}

	@Override
	public Slice<Post> searchFirstPostsByKeyword(Long userUid, Geometry point, short distanceMeters, String keyword,
		PageRequest pageRequest) {
		List<PostDoc> postDocs = postDocElasticRepository.searchPostDocBy(keyword);
		List<Long> postIds = postDocs.stream().map(PostDoc::getId).toList();
		return postJpaRepository.findAllByIdIn(userUid, point, distanceMeters, postIds, pageRequest);
	}

	@Override
	public Slice<Post> searchPostsByKeyword(Long userUid, Geometry point, short distanceMeters, String keyword,
		Long cursor, PageRequest pageRequest) {
		List<PostDoc> postDocs = postDocElasticRepository.searchPostDocBy(cursor, keyword);
		List<Long> postIds = postDocs.stream().map(PostDoc::getId).toList();
		return postJpaRepository.findAllByIdIn(userUid, point, distanceMeters, postIds, pageRequest);
	}

	@Override
	public Slice<Post> findAllReportedPost(Integer reportsCount, Long cursor, PageRequest pageRequest) {
		return postJpaRepository.findReportedPostPage(reportsCount, cursor, pageRequest);
	}
}
