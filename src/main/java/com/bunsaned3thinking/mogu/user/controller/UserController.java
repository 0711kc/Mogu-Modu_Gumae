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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserComponentService userComponentService;
	private final ObjectMapper objectMapper;

	@PostMapping
	public ResponseEntity<UserDetailResponse> createUser(@RequestBody @Valid final UserRequest userRequest) {
		return userComponentService.createUser(userRequest);
	}

	@GetMapping("/my")
	public ResponseEntity<UserDetailResponse> findUser(Principal principal) {
		return userComponentService.findUser(principal.getName());
	}

	@GetMapping("/other/{userId}")
	public ResponseEntity<UserResponse> findOtherUser(@PathVariable final String userId) {
		return userComponentService.findOtherUser(userId);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDetailResponse> findUser(@PathVariable final String userId) {
		return userComponentService.findUser(userId);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDetailResponse>> findAllUser(
		@RequestParam(name = "cursor", required = false, defaultValue = "0") final Long cursor) {
		return userComponentService.findAllUser(cursor);
	}

	@GetMapping("/saving")
	public ResponseEntity<SavingCostResponse> findUserSavingCost(Principal principal) {
		return userComponentService.findUserSavingCost(principal.getName());
	}

	@GetMapping("/level")
	public ResponseEntity<LevelResponse> findUserLevel(Principal principal) {
		return userComponentService.findUserLevel(principal.getName());
	}

	@PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<UserDetailResponse> updateUser(Principal principal,
		@RequestPart(name = "request", required = false) final Optional<String> updateUserRequestJson,
		@RequestPart(name = "image", required = false) MultipartFile multipartFile) throws JsonProcessingException {
		@Valid UpdateUserRequest updateUserRequest = updateUserRequestJson.isPresent()
			? objectMapper.readValue(updateUserRequestJson.get(), UpdateUserRequest.class)
			: null;
		return userComponentService.updateUser(principal.getName(), updateUserRequest, multipartFile);
	}

	@PatchMapping("/password")
	public ResponseEntity<UserDetailResponse> updateUserPassword(Principal principal,
		@RequestBody @Valid final UpdateUserPasswordRequest updateUserPasswordRequest) {
		return userComponentService.updateUserPassword(principal.getName(), updateUserPasswordRequest);
	}

	@DeleteMapping("/my")
	public ResponseEntity<Void> deleteMy(Principal principal) {
		return userComponentService.deleteUser(principal.getName());
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable final String userId) {
		return userComponentService.deleteUser(userId);
	}

	@PatchMapping("/block/{userId}")
	public ResponseEntity<UserDetailResponse> setBlockUser(@PathVariable final String userId,
		@RequestPart(name = "isBlock") Boolean state) {
		return userComponentService.setBlockUser(userId, state);
	}

	@PatchMapping("/manner/post/{postId}/receiver/{receiverId}")
	public ResponseEntity<UserDetailResponse> updateUserManner(
		Principal principal,
		@PathVariable final String receiverId,
		@PathVariable final Long postId,
		@RequestPart(name = "manner") Manner manner) {
		return userComponentService.updateUserManner(principal.getName(), receiverId, manner, postId);
	}
}
