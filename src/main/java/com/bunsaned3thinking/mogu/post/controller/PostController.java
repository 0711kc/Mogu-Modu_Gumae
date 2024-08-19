package com.bunsaned3thinking.mogu.post.controller;

import java.security.Principal;
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
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.post.service.component.PostComponentService;
import com.bunsaned3thinking.mogu.report.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.report.dto.response.ReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	private final PostComponentService postComponentService;
	private final ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<PostWithDetailResponse> createPost(
		Principal principal,
		@RequestPart(name = "request") final String postRequestJson,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList)
		throws JsonProcessingException {
		@Valid final PostRequest postRequest = objectMapper.readValue(postRequestJson, PostRequest.class);
		return postComponentService.createPost(principal.getName(), postRequest,
			multipartFileList.orElseGet(ArrayList::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findPost(@PathVariable final Long id) {
		return postComponentService.findPost(id);
	}

	@GetMapping("/detail/{id}")
	public ResponseEntity<PostWithDetailResponse> findPostWithDetail(@PathVariable final Long id) {
		return postComponentService.findPostWithDetail(id);
	}

	@GetMapping("/all")
	public ResponseEntity<List<PostResponse>> findPostAll(
		Principal principal,
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllPosts(principal.getName(), cursor);
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<PostWithDetailResponse> updatePost(
		@PathVariable Long postId,
		Principal principal,
		@RequestPart(name = "request") final Optional<String> updatePostRequestJson,
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList)
		throws JsonProcessingException {
		@Valid final UpdatePostRequest updatePostRequest = updatePostRequestJson.isPresent()
			? objectMapper.readValue(updatePostRequestJson.get(), UpdatePostRequest.class)
			: null;
		return postComponentService.updatePost(postId, principal.getName(), updatePostRequest,
			multipartFileList.orElseGet(ArrayList::new));
	}

	@PatchMapping("/{postId}/close")
	public ResponseEntity<PostResponse> closePost(
		@PathVariable final Long postId,
		Principal principal,
		@RequestPart(name = "state") RecruitState recruitState) {
		return postComponentService.closePost(postId, principal.getName(), recruitState);
	}

	@PatchMapping("/{postId}/hide")
	public ResponseEntity<PostResponse> hidePost(
		@PathVariable final Long postId,
		Principal principal,
		@RequestPart(name = "state") boolean state) {
		return postComponentService.hidePost(postId, principal.getName(), state);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable final Long postId, Principal principal) {
		return postComponentService.deletePost(principal.getName(), postId);
	}

	@PostMapping("/report/{postId}")
	public ResponseEntity<ReportResponse> createReport(
		@PathVariable final Long postId,
		Principal principal,
		@RequestBody @Valid final ReportRequest reportRequest) {
		return postComponentService.createReport(postId, principal.getName(), reportRequest);
	}

	@GetMapping("/reports")
	public ResponseEntity<List<PostResponse>> findAllReportedPost(
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllReportedPost(cursor);
	}

	@GetMapping("/search")
	public ResponseEntity<List<PostResponse>> searchPosts(
		Principal principal,
		@RequestParam(name = "keyword") String keyword,
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.searchPostByKeyword(keyword, principal.getName(), cursor);
	}

	@GetMapping("/search/histories")
	public ResponseEntity<List<SearchHistoryResponse>> findAllSearchHistory(Principal principal) {
		return postComponentService.findAllSearchHistory(principal.getName());
	}

	@DeleteMapping("/search/history/{searchHistoryId}")
	public ResponseEntity<Void> deleteSearchHistory(@PathVariable final Long searchHistoryId,
		Principal principal) {
		return postComponentService.deleteSearchHistory(searchHistoryId, principal.getName());
	}

	@PostMapping("/{postId}/like")
	public ResponseEntity<PostResponse> likePost(@PathVariable final Long postId, Principal principal) {
		return postComponentService.likePost(postId, principal.getName());
	}

	@DeleteMapping("/{postId}/unlike")
	public ResponseEntity<Void> unlikePost(@PathVariable final Long postId, Principal principal) {
		return postComponentService.unlikePost(postId, principal.getName());
	}

	@GetMapping("/all/like")
	public ResponseEntity<List<PostResponse>> findAllLikePost(
		Principal principal,
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllLikedPost(principal.getName(), cursor);
	}
}
