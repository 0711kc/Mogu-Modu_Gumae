package com.bunsaned3thinking.mogu.ask.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.bunsaned3thinking.mogu.ask.controller.dto.response.AskResponse;
import com.bunsaned3thinking.mogu.ask.service.component.AskComponentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "참여 요청 API", description = "참여 요청 관련 API 입니다.")
@RequestMapping("/ask")
@RequiredArgsConstructor
public class AskController {
	private final AskComponentService askComponentService;

	@PostMapping("/user/{userId}/post/{postId}")
	@Operation(summary = "참여 요청 생성", description = "참여 요청 정보를 생성합니다.")
	public ResponseEntity<AskResponse> createAsk(
		@Parameter(name = "User ID", description = "신청자의 id", in = ParameterIn.PATH)
		@PathVariable final String userId,
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId) {
		return askComponentService.createAsk(userId, postId);
	}

	@GetMapping("/user/{userId}")
	@Operation(summary = "사용자 참여 요청 조회", description = "특정 사용자의 모든 참여 요청 정보를 조회합니다.")
	public ResponseEntity<List<AskResponse>> findAskByUser(
		@Parameter(name = "User ID", description = "신청자의 id", in = ParameterIn.PATH)
		@PathVariable final String userId) {
		return askComponentService.findAskByUser(userId);
	}

	@GetMapping("/post/{postId}")
	@Operation(summary = "게시글 참여 요청 조회", description = "특정 게시글의 모든 참여 요청 정보를 조회합니다.")
	public ResponseEntity<List<AskResponse>> findAskByPost(
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId) {
		return askComponentService.findAskByPost(postId);
	}

	@PatchMapping("/user/{userId}/post/{postId}")
	@Operation(summary = "참여 요청 수정", description = "참여 요청 정보의 상태 정보를 수정합니다.")
	public ResponseEntity<AskResponse> updateAskState(
		@Parameter(name = "User ID", description = "신청자의 id", in = ParameterIn.PATH)
		@PathVariable final String userId,
		@Parameter(name = "Post ID", description = "게시글의 id", in = ParameterIn.PATH)
		@PathVariable final Long postId,
		@Parameter(description = "참여 요청 상태")
		@RequestPart(name = "state") Boolean state) {
		return askComponentService.updateAsk(userId, postId, state);
	}
}
