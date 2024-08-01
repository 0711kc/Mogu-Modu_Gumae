package com.bunsaned3thinking.mogu.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;
import com.bunsaned3thinking.mogu.post.service.component.PostComponentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	private final PostComponentService postComponentService;

	@PostMapping("/{userId}")
	public ResponseEntity<PostWithDetailResponse> createPost(
		@PathVariable String userId,
		@RequestPart(name = "request") @Valid final PostRequest postRequest,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList) {
		return postComponentService.createPost(userId, postRequest, multipartFileList.orElseGet(ArrayList::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findPost(@PathVariable final Long id) {
		return postComponentService.findPost(id);
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<PostWithDetailResponse> findPostWithDetail(@PathVariable final Long id) {
		return postComponentService.findPostWithDetail(id);
	}

	@PatchMapping("/{postId}/{userId}")
	public ResponseEntity<PostWithDetailResponse> updatePost(
		@PathVariable Long postId,
		@PathVariable String userId,
		@RequestPart(name = "request") @Valid final UpdatePostRequest updatePostRequest,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList) {
		return postComponentService.updatePost(postId, userId, updatePostRequest,
			multipartFileList.orElseGet(ArrayList::new));
	}

	@DeleteMapping("/{postId}/{userId}")
	public ResponseEntity<Void> deletePost(@PathVariable final Long postId, @PathVariable final String userId) {
		return postComponentService.deletePost(userId, postId);
	}
}
