package com.busaned_thinking.mogu.post.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busaned_thinking.mogu.config.S3Config;
import com.busaned_thinking.mogu.location.entity.Location;
import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;
import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.post.entity.PostDetail;
import com.busaned_thinking.mogu.post.entity.PostImage;
import com.busaned_thinking.mogu.post.repository.PostDetailRepository;
import com.busaned_thinking.mogu.post.repository.PostRepository;
import com.busaned_thinking.mogu.user.entity.User;
import com.busaned_thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostDetailRepository postDetailRepository;

	@Override
	public ResponseEntity<PostResponse> createPost(String userId, PostRequest postRequest, Location location,
		List<String> postImageLinks) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		List<PostImage> postImages = createPostImages(postImageLinks);
		PostDetail postDetail = postRequest.toDetailEntity(postImages);
		Post post = postRequest.toEntity(user, location, postDetail);
		Post savedPost = postRepository.save(post);
		postDetailRepository.save(postDetail);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(savedPost));
	}

	private List<PostImage> createPostImages(List<String> postImageLinks) {
		if (postImageLinks.isEmpty()) {
			return List.of(PostImage.from(S3Config.basicPostImage()));
		}
		return postImageLinks.stream()
			.map(PostImage::from)
			.toList();
	}

	@Override
	public ResponseEntity<PostResponse> findPost(Long id) {
		return null;
	}

	@Override
	public ResponseEntity<PostResponse> updatePost(Long id, UpdatePostRequest updatePostRequest) {
		return null;
	}

	private void update(Post post, UpdatePostRequest updatePostRequest) {
	}

	@Override
	public ResponseEntity<Void> deletePost(Long id) {
		return null;
	}

	private static void copyNonNullProperties(Object src, Object target) {
	}

	private static String[] getNullPropertyNames(Object source) {
		return null;
	}
}
