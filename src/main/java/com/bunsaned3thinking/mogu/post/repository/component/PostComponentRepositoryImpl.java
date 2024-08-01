package com.bunsaned3thinking.mogu.post.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.repository.module.PostDetailRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostImageRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostComponentRepositoryImpl implements PostComponentRepository {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostDetailRepository postDetailRepository;
	private final PostImageRepository postImageRepository;

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
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> findPostById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public void deletePostDetailByPostId(Long id) {
		postDetailRepository.deleteByPostId(id);
	}
}
