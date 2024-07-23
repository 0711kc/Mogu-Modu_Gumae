package com.busaned_thinking.mogu.post.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;
import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	@Override
	public ResponseEntity<PostResponse> createPost(PostRequest postRequest) {
		return null;
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
