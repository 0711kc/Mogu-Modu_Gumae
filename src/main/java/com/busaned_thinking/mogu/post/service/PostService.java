package com.busaned_thinking.mogu.post.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.location.entity.Location;
import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;

public interface PostService {

	ResponseEntity<Void> deletePost(String userId, Long postId);

	ResponseEntity<PostResponse> createPost(String memberId, PostRequest postRequest, Location location,
		List<String> postImageLinks);

	ResponseEntity<PostResponse> findPost(Long id);

	ResponseEntity<PostResponse> updatePost(Long id, UpdatePostRequest updatePostRequest);
}
