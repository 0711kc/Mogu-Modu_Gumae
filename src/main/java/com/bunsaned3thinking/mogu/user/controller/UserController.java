package com.bunsaned3thinking.mogu.user.controller;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
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
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid final UserRequest userRequest) {
		return userComponentService.createUser(userRequest);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> findUser(@PathVariable final String userId) {
		return userComponentService.findUser(userId);
	}

	@GetMapping("/{userId}/saving")
	public ResponseEntity<SavingCostResponse> findUserSavingCost(@PathVariable final String userId) {
		return userComponentService.findUserSavingCost(userId);
	}

	@GetMapping("/{userId}/level")
	public ResponseEntity<LevelResponse> findUserLevel(@PathVariable final String userId) {
		return userComponentService.findUserLevel(userId);
	}

	@PatchMapping(value = "/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<UserResponse> updateUser(@PathVariable final String userId,
		@RequestPart(name = "request", required = false) final Optional<String> updateUserRequestJson,
		@RequestPart(name = "image", required = false) MultipartFile multipartFile) throws JsonProcessingException {
		@Valid UpdateUserRequest updateUserRequest = null;
		if (updateUserRequestJson.isPresent()) {
			updateUserRequest = objectMapper.readValue(updateUserRequestJson.get(), UpdateUserRequest.class);
		}
		return userComponentService.updateUser(userId, updateUserRequest, multipartFile);
	}

	@PatchMapping("/{userId}/password")
	public ResponseEntity<UserResponse> updateUserPassword(@PathVariable final String userId,
		@RequestBody @Valid final UpdateUserPasswordRequest updateUserPasswordRequest) {
		return userComponentService.updateUserPassword(userId, updateUserPasswordRequest);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable final String userId) {
		return userComponentService.deleteUser(userId);
	}

	@PatchMapping("/block/{userId}")
	public ResponseEntity<UserResponse> setBlockUser(@PathVariable final String userId,
		@RequestPart(name = "isBlock") Boolean state) {
		return userComponentService.setBlockUser(userId, state);
	}

	@PatchMapping("/manner/sender/{senderId}/receiver/{receiverId}/{postId}")
	public ResponseEntity<UserResponse> updateUserManner(
		@PathVariable final String senderId,
		@PathVariable final String receiverId,
		@PathVariable final Long postId,
		@RequestPart(name = "manner") Manner manner) {
		return userComponentService.updateUserManner(senderId, receiverId, manner, postId);
	}
}
