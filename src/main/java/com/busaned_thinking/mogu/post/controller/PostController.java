package com.busaned_thinking.mogu.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;
import com.busaned_thinking.mogu.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@PostMapping("/new")
	public ResponseEntity<PostResponse> createPost(@RequestBody @Valid final PostRequest postRequest) {
		return postService.createPost(postRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findPost(@PathVariable final Long id) {
		return postService.findPost(id);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<PostResponse> updatePost(@PathVariable final Long id,
		@RequestBody @Valid UpdatePostRequest updatePostRequest) {
		return postService.updatePost(id, updatePostRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable final Long id) {
		return postService.deletePost(id);
	}
}
