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
import com.bunsaned3thinking.mogu.report.entity.Report;
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
	List<Post> findLikedPostsByUserId(String userId);

	// Report
	Report saveReport(Report report);

	boolean isReportExists(Long postId, Long userUid);

	Slice<Post> findAllReportedPost(Long cursor, PageRequest pageRequest);

	// Search
	SearchHistory saveSearchHistory(String keyword, User user);

	List<Post> searchPostsByTitle(String keyword);

	Optional<SearchHistory> findSearchHistoryById(Long searchHistoryId);

	void deleteSearchHistoryById(Long searchHistoryId);
}
