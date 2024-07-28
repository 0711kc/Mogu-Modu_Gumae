package com.busaned_thinking.mogu.post.service;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;

public interface PostService {

	ResponseEntity<PostResponse> createPost(PostRequest postRequest);

	ResponseEntity<Void> deletePost(Long id);

	ResponseEntity<PostResponse> findPost(Long id);

	ResponseEntity<PostResponse> updatePost(Long id, UpdatePostRequest updatePostRequest);
}
