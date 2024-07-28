package com.busaned_thinking.mogu.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busaned_thinking.mogu.image.service.ImageService;
import com.busaned_thinking.mogu.location.entity.Location;
import com.busaned_thinking.mogu.location.service.LocationService;
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
	private final LocationService locationService;
	private final ImageService imageService;

	@PostMapping("/new/{userId}")
	public ResponseEntity<PostResponse> createPost(
		@PathVariable String userId,
		@RequestPart(name = "request") @Valid final PostRequest postRequest,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList) {
		List<String> imageLinks = imageService.uploadAll(multipartFileList.orElseGet(ArrayList::new));
		Location location = locationService.createLocation(postRequest.getLongitude(), postRequest.getLatitude());
		return postService.createPost(userId, postRequest, location, imageLinks);
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

	@DeleteMapping("/{postId}/{userId}")
	public ResponseEntity<Void> deletePost(@PathVariable final Long postId, @PathVariable final String userId) {
		return postService.deletePost(userId, postId);
	}
}
