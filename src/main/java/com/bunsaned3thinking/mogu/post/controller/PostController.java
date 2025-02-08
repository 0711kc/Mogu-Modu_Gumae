package com.bunsaned3thinking.mogu.post.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "게시글 API", description = "게시글 관련 API 입니다.")
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	private final PostComponentService postComponentService;
	private final ObjectMapper objectMapper;

	@PostMapping
	@Operation(summary = "게시글 생성", description = "게시글 정보를 생성합니다.")
	public ResponseEntity<PostWithDetailResponse> createPost(
		Principal principal,
		@Parameter(description = "생성할 게시글 정보")
		@RequestPart(name = "request") final String postRequestJson,
		@Parameter(description = "게시글 사진 리스트")
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList)
		throws JsonProcessingException {
		@Valid final PostRequest postRequest = objectMapper.readValue(postRequestJson, PostRequest.class);
		return postComponentService.createPost(principal.getName(), postRequest,
			multipartFileList.orElseGet(ArrayList::new));
	}

	@GetMapping("/{id}")
	@Operation(summary = "게시글 조회", description = "게시글 정보를 조회합니다.")
	public ResponseEntity<PostResponse> findPost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long id) {
		return postComponentService.findPost(id);
	}

	@GetMapping("/detail/{id}")
	@Operation(summary = "게시글 상세 조회", description = "게시글 상세 정보를 조회합니다.")
	public ResponseEntity<PostWithDetailResponse> findPostWithDetail(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long id) {
		return postComponentService.findPostWithDetail(id);
	}

	@GetMapping("/all")
	@Operation(summary = "모든 게시글 조회", description = "게시글 정보를 조회합니다.")
	public ResponseEntity<List<PostResponse>> findPostAll(
		Principal principal,
		@Parameter(name = "Cursor", description = "마지막 조회 게시글 번호", in = ParameterIn.QUERY)
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllPosts(principal.getName(), cursor);
	}

	@PatchMapping("/{postId}")
	@Operation(summary = "게시글 수정", description = "게시글 정보를 수정합니다.")
	public ResponseEntity<PostWithDetailResponse> updatePost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable Long postId,
		Principal principal,
		@Parameter(description = "수정할 게시글 정보")
		@RequestPart(name = "request") final Optional<String> updatePostRequestJson,
		@Parameter(description = "게시글 사진 리스트")
		@RequestPart(value = "multipartFileList", required = false) Optional<List<MultipartFile>> multipartFileList)
		throws JsonProcessingException {
		@Valid final UpdatePostRequest updatePostRequest = updatePostRequestJson.isPresent()
			? objectMapper.readValue(updatePostRequestJson.get(), UpdatePostRequest.class)
			: null;
		return postComponentService.updatePost(postId, principal.getName(), updatePostRequest,
			multipartFileList.orElseGet(ArrayList::new));
	}

	@PatchMapping(value = "/{postId}/close",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "게시글 닫기", description = "게시글 모집 상태를 변경합니다.")
	public ResponseEntity<PostResponse> closePost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId,
		Principal principal,
		@Parameter(description = "모집 상태")
		@RequestPart(name = "state") RecruitState recruitState) {
		return postComponentService.closePost(postId, principal.getName(), recruitState);
	}

	@PatchMapping("/{postId}/hide")
	@Operation(summary = "게시글 숨기기", description = "게시글을 숨김 상태를 변경합니다.")
	public ResponseEntity<PostResponse> hidePost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId,
		Principal principal,
		@Parameter(description = "숨김 여부")
		@RequestPart(name = "state") boolean state) {
		return postComponentService.hidePost(postId, principal.getName(), state);
	}

	@DeleteMapping("/{postId}")
	@Operation(summary = "게시글 삭제", description = "게시글 정보를 삭제합니다.")
	public ResponseEntity<Void> deletePost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId, Principal principal) {
		return postComponentService.deletePost(principal.getName(), postId);
	}

	@PostMapping("/report/{postId}")
	@Operation(summary = "게시글 신고", description = "게시글을 신고합니다.")
	public ResponseEntity<ReportResponse> createReport(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId,
		Principal principal,
		@Parameter(description = "게시글 신고 정보")
		@RequestBody @Valid final ReportRequest reportRequest) {
		return postComponentService.createReport(postId, principal.getName(), reportRequest);
	}

	@GetMapping("/reports")
	@Operation(summary = "신고된 모든 게시글 조회 (관리자)", description = "신고된 모든 게시글을 조회합니다.")
	public ResponseEntity<List<PostResponse>> findAllReportedPost(
		@Parameter(name = "Cursor", description = "마지막 조회 신고된 게시글 번호", in = ParameterIn.PATH)
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllReportedPost(cursor);
	}

	@GetMapping("/search")
	@Operation(summary = "게시글 검색", description = "키워드를 이용하여 게시글 정보들을 검색합니다.")
	public ResponseEntity<List<PostResponse>> searchPosts(
		Principal principal,
		@Parameter(name = "Keyword", description = "게시글을 검색할 키워드", in = ParameterIn.PATH)
		@RequestParam(name = "keyword") String keyword,
		@Parameter(name = "Cursor", description = "마지막 조회 검색된 게시글 번호", in = ParameterIn.PATH)
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.searchPostByKeyword(keyword, principal.getName(), cursor);
	}

	@GetMapping("/search/histories")
	@Operation(summary = "게시글 검색 기록 조회", description = "게시글 검색 기록을 조회합니다.")
	public ResponseEntity<List<SearchHistoryResponse>> findAllSearchHistory(Principal principal) {
		return postComponentService.findAllSearchHistory(principal.getName());
	}

	@DeleteMapping("/search/history/{searchHistoryId}")
	@Operation(summary = "게시글 검색 기록 삭제", description = "게시글 검색 기록을 삭제합니다.")
	public ResponseEntity<Void> deleteSearchHistory(
		@Parameter(name = "Search History ID", description = "검색 기록의 id", in = ParameterIn.PATH)
		@PathVariable final Long searchHistoryId,
		Principal principal) {
		return postComponentService.deleteSearchHistory(searchHistoryId, principal.getName());
	}

	@PostMapping("/{postId}/like")
	@Operation(summary = "게시글 좋아요", description = "게시글에 좋아요를 추가합니다.")
	public ResponseEntity<PostResponse> likePost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId, Principal principal) {
		return postComponentService.likePost(postId, principal.getName());
	}

	@DeleteMapping("/{postId}/unlike")
	@Operation(summary = "게시글 좋아요 취소", description = "게시글에서 좋아요를 삭제합니다.")
	public ResponseEntity<Void> unlikePost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId, Principal principal) {
		return postComponentService.unlikePost(postId, principal.getName());
	}

	@GetMapping("/all/like")
	@Operation(summary = "좋아요 표시한 모든 게시글 조회", description = "좋아요 표시한 모든 게시글 정보를 조회합니다.")
	public ResponseEntity<List<PostResponse>> findAllLikePost(
		Principal principal,
		@Parameter(name = "Cursor", description = "마지막 조회 좋아요 표시한 게시글 번호", in = ParameterIn.PATH)
		@RequestParam(name = "cursor", required = false, defaultValue = "0") Long cursor) {
		return postComponentService.findAllLikedPost(principal.getName(), cursor);
	}
}
