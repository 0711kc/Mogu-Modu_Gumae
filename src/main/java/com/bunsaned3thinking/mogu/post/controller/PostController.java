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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.SearchHistoryResponse;
import com.bunsaned3thinking.mogu.post.service.component.PostComponentService;
import com.bunsaned3thinking.mogu.report.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.report.dto.response.ReportResponse;

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

	@GetMapping("/all/{userId}")
	public ResponseEntity<List<PostResponse>> findPostAll(
		@PathVariable final String userId,
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllPosts(userId, cursor);
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

	@PatchMapping("/{postId}/{userId}/close")
	public ResponseEntity<PostResponse> closePost(@PathVariable final Long postId, @PathVariable final String userId) {
		return postComponentService.closePost(postId, userId);
	}

	@PatchMapping("/{postId}/{userId}/hide")
	public ResponseEntity<PostResponse> hidePost(
		@PathVariable final Long postId,
		@PathVariable final String userId,
		@RequestPart(name = "state") boolean state) {
		return postComponentService.hidePost(postId, userId, state);
	}

	@DeleteMapping("/{postId}/{userId}")
	public ResponseEntity<Void> deletePost(@PathVariable final Long postId, @PathVariable final String userId) {
		return postComponentService.deletePost(userId, postId);
	}

	@PostMapping("/report/{postId}/{userId}")
	public ResponseEntity<ReportResponse> createReport(
		@PathVariable final Long postId,
		@PathVariable final String userId,
		@RequestBody @Valid final ReportRequest reportRequest) {
		return postComponentService.createReport(postId, userId, reportRequest);
	}

	@GetMapping("/reports")
	public ResponseEntity<List<PostResponse>> findAllReportedPost(
		@RequestParam(name = "cursor", required = false) Long cursor) {
		return postComponentService.findAllReportedPost(cursor);
	}

	@GetMapping("/search/{userId}")
	public ResponseEntity<List<PostResponse>> searchPosts(@RequestParam(name = "title") String title,
		@PathVariable final String userId) {
		return postComponentService.searchPostByTitle(title, userId);
	}

	@GetMapping("/searchHistories/{userId}")
	public ResponseEntity<List<SearchHistoryResponse>> findAllSearchHistory(@PathVariable final String userId) {
		return postComponentService.findAllSearchHistory(userId);
	}

	@DeleteMapping("/searchHistory/{searchHistoryId}/{userId}")
	public ResponseEntity<Void> deleteSearchHistory(@PathVariable final Long searchHistoryId,
		@PathVariable final String userId) {
		return postComponentService.deleteSearchHistory(searchHistoryId, userId);
	}

	@PostMapping("/{postId}/like/{userId}")
	public ResponseEntity<PostResponse> likePost(@PathVariable final Long postId, @PathVariable final String userId) {
		return postComponentService.likePost(postId, userId);
	}

	@DeleteMapping("/{postId}/like/{userId}")
	public ResponseEntity<Void> unlikePost(@PathVariable final Long postId, @PathVariable final String userId) {
		return postComponentService.unlikePost(postId, userId);
	}

	@GetMapping("/all/like/{userId}")
	public ResponseEntity<List<PostResponse>> findAllLikePost(@PathVariable final String userId) {
		return postComponentService.findAllLikedPost(userId);
	}
}
