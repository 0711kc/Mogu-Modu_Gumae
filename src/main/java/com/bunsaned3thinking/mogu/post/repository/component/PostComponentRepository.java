package com.bunsaned3thinking.mogu.post.repository.component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.HiddenPostId;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface PostComponentRepository {
	// User
	Optional<User> findUserByUserId(String userId);

	// Post
	Optional<Post> findPostById(Long id);

	void saveAllPostImages(List<PostImage> postImages);

	void savePostDetail(PostDetail postDetail);

	Post savePost(Post post);

	void deletePostDetailByPostId(Long id);

	List<Post> findAllPostsByPurchaseDate(LocalDate purchaseDate);

	void saveAllPosts(List<Post> posts);

	Slice<Post> findNextPagePosts(Long userUid, Long cursor, PageRequest pageRequest, Point referencePoint,
		Short distanceMeters);

	// HiddenPost
	void saveHiddenPost(HiddenPost hiddenPost);

	void deleteHiddenPostById(HiddenPostId hiddenPostId);

	boolean existsHiddenPostById(HiddenPostId hiddenPostId);

	// LikedPost
	Slice<Post> findLikedPostsByUserId(String userId, Long cursor, PageRequest pageRequest);

	// Report
	Slice<Post> findAllReportedPost(Integer reportsCount, Long cursor, PageRequest pageRequest);

	// Search
	SearchHistory saveSearchHistory(String keyword, User user);

	Slice<Post> findLikedPostsFirstPageByUserId(String userId, PageRequest pageRequest);

	Slice<Post> searchPostsByTitle(String keyword, Long cursor, PageRequest pageRequest);

	Optional<SearchHistory> findSearchHistoryById(Long searchHistoryId);

	void deleteSearchHistoryById(Long searchHistoryId);

	Slice<Post> findAllFirstPageReportedPost(PageRequest pageRequest);

	Slice<Post> findFirstPagePosts(Long userUid, PageRequest pageRequest, Point referencePoint, Short distanceMeters);
}
