package com.bunsaned3thinking.mogu.user.controller;

import java.security.Principal;
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

import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserDetailResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;
import com.bunsaned3thinking.mogu.user.entity.Manner;
import com.bunsaned3thinking.mogu.user.service.component.UserComponentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Tag(name = "사용자 API", description = "사용자 관련 API 입니다.")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserComponentService userComponentService;
	private final ObjectMapper objectMapper;

	@PostMapping
	@Operation(summary = "사용자 생성", description = "사용자 정보를 생성합니다.")
	public ResponseEntity<UserDetailResponse> createUser(@RequestBody @Valid final UserRequest userRequest) {
		return userComponentService.createUser(userRequest);
	}

	@GetMapping("/my")
	@Operation(summary = "사용자 본인 조회", description = "사용자 본인의 정보를 조회합니다.")
	public ResponseEntity<UserDetailResponse> findUser(Principal principal) {
		return userComponentService.findUser(principal.getName());
	}

	@GetMapping("/other/{userId}")
	@Operation(summary = "다른 사용자 조회", description = "다른 사용자의 정보를 조회합니다.")
	public ResponseEntity<UserResponse> findOtherUser(
		@Parameter(name = "User ID", description = "사용자의 id", in = ParameterIn.PATH)
		@PathVariable final String userId) {
		return userComponentService.findOtherUser(userId);
	}

	@GetMapping("/{userId}")
	@Operation(summary = "사용자 조회 (관리자)", description = "사용자의 모든 정보를 조회합니다.")
	public ResponseEntity<UserDetailResponse> findUser(@PathVariable final String userId) {
		return userComponentService.findUser(userId);
	}

	@GetMapping("/all")
	@Operation(summary = "모든 사용자 조회", description = "모든 사용자의 정보를 조회합니다.")
	public ResponseEntity<List<UserDetailResponse>> findAllUser(
		@Parameter(name = "Cursor", description = "마지막 조회 사용자 번호", in = ParameterIn.QUERY)
		@RequestParam(name = "cursor", required = false, defaultValue = "0") final Long cursor) {
		return userComponentService.findAllUser(cursor);
	}

	@GetMapping("/saving")
	@Operation(summary = "아낀 비용 조회", description = "자신이 아낀 비용을 조회합니다.")
	public ResponseEntity<SavingCostResponse> findUserSavingCost(Principal principal) {
		return userComponentService.findUserSavingCost(principal.getName());
	}

	@GetMapping("/level")
	@Operation(summary = "레벨 조회", description = "본인의 레벨을 조회합니다.")
	public ResponseEntity<LevelResponse> findUserLevel(Principal principal) {
		return userComponentService.findUserLevel(principal.getName());
	}

	@PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@Operation(summary = "사용자 수정", description = "사용자의 정보를 수정합니다.")
	public ResponseEntity<UserDetailResponse> updateUserWithProfile(Principal principal,
		@Parameter(name = "Is Update Profile", description = "프로필 이미지 업데이트 여부", in = ParameterIn.QUERY)
		@RequestParam(name = "profile", required = false, defaultValue = "false") final Boolean isUpdateProfile,

		@RequestPart(name = "request", required = false) final Optional<String> updateUserRequestJson,
		@RequestPart(name = "image", required = false) MultipartFile multipartFile) throws JsonProcessingException {
		@Valid UpdateUserRequest updateUserRequest = updateUserRequestJson.isPresent()
			? objectMapper.readValue(updateUserRequestJson.get(), UpdateUserRequest.class)
			: null;
		if (isUpdateProfile) {
			return userComponentService.updateUserWithProfile(principal.getName(), updateUserRequest, multipartFile);
		}
		return userComponentService.updateUser(principal.getName(), updateUserRequest);
	}

	@PatchMapping("/password")
	@Operation(summary = "사용자 조회", description = "사용자 본인의 정보를 조회합니다.")
	public ResponseEntity<UserDetailResponse> updateUserPassword(Principal principal,
		@RequestBody @Valid final UpdateUserPasswordRequest updateUserPasswordRequest) {
		return userComponentService.updateUserPassword(principal.getName(), updateUserPasswordRequest);
	}

	@DeleteMapping("/my")
	@Operation(summary = "사용자 삭제", description = "사용자 본인의 정보를 삭제합니다.")
	public ResponseEntity<Void> deleteMy(Principal principal) {
		return userComponentService.deleteUser(principal.getName());
	}

	@DeleteMapping("/{userId}")
	@Operation(summary = "다른 사용자 삭제 (관리자)", description = "다른 사용자 정보를 삭제합니다.")
	public ResponseEntity<Void> deleteUser(
		@Parameter(name = "User ID", description = "사용자의 id", in = ParameterIn.PATH)
		@PathVariable final String userId) {
		return userComponentService.deleteUser(userId);
	}

	@PatchMapping("/block/{userId}")
	@Operation(summary = "사용자 차단(관리자)", description = "사용자를 차단합니다.")
	public ResponseEntity<UserDetailResponse> setBlockUser(
		@Parameter(name = "User ID", description = "사용자의 id", in = ParameterIn.PATH)
		@PathVariable final String userId,

		@RequestPart(name = "isBlock") Boolean state) {
		return userComponentService.setBlockUser(userId, state);
	}

	@PatchMapping("/manner/post/{postId}/receiver/{receiverId}")
	@Operation(summary = "사용자 평가", description = "다른 사용자의 매너를 평가합니다.")
	public ResponseEntity<UserDetailResponse> updateUserManner(
		Principal principal,
		@Parameter(name = "Receiver ID", description = "평가받는 사용자 id", in = ParameterIn.PATH)
		@PathVariable final String receiverId,

		@Parameter(name = "Post ID", description = "평가 게시글 id", in = ParameterIn.PATH)
		@PathVariable final Long postId,

		@RequestPart(name = "manner") Manner manner) {
		return userComponentService.updateUserManner(principal.getName(), receiverId, manner, postId);
	}
}
