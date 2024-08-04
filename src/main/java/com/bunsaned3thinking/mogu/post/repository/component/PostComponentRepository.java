package com.bunsaned3thinking.mogu.post.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.entity.Report;
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
}
