package com.bunsaned3thinking.mogu.post.repository.component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface PostComponentRepository {
	Optional<User> findUserByUserId(String userId);

	void saveAllPostImages(List<PostImage> postImages);

	void savePostDetail(PostDetail postDetail);

	Post savePost(Post post);

	Report saveReport(Report report);

	SearchHistory saveSearchHistory(String keyword, User user);

	Optional<Post> findPostById(Long id);

	Optional<SearchHistory> findSearchHistoryById(Long searchHistoryId);

	List<Post> searchPostsByTitle(String keyword);

	List<Report> findAllReport();

	boolean isReportExists(Post post, User user);

	List<Post> findAllReportedPost();

	void deletePostDetailByPostId(Long id);

	void deleteSearchHistoryById(Long searchHistoryId);

	List<Post> findAllPostsByPurchaseDate(LocalDate purchaseDate);

	void saveAllPosts(List<Post> posts);

	Slice<Post> findNextPagePosts(Long userUid, Long cursor, PageRequest pageRequest);

	void saveHiddenPost(HiddenPost hiddenPost);
}
