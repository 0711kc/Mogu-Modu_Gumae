package com.busaned_thinking.mogu.post.repository.component;

import java.util.List;
import java.util.Optional;

import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.post.entity.PostDetail;
import com.busaned_thinking.mogu.post.entity.PostImage;
import com.busaned_thinking.mogu.user.entity.User;

public interface PostComponentRepository {
	Optional<User> findUserByUserId(String userId);

	void saveAllPostImages(List<PostImage> postImages);

	void savePostDetail(PostDetail postDetail);

	Post savePost(Post post);

	Optional<Post> findPostById(Long id);

	void deletePostDetailByPostId(Long id);
}
