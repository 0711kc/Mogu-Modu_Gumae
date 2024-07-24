package com.busaned_thinking.mogu.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.location.entity.ActivityArea;
import com.busaned_thinking.mogu.location.service.ActivityAreaService;
import com.busaned_thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.busaned_thinking.mogu.user.controller.dto.request.UserRequest;
import com.busaned_thinking.mogu.user.controller.dto.response.UserResponse;
import com.busaned_thinking.mogu.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final ActivityAreaService activityAreaService;

	@PostMapping("/new")
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid final UserRequest userRequest) {
		ActivityArea activityArea = activityAreaService.create(userRequest.getLongitude(), userRequest.getLatitude());
		return userService.createUser(userRequest, activityArea);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> findUser(@PathVariable final String userId) {
		return userService.findUser(userId);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable final String userId,
		@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		return userService.updateUser(userId, updateUserRequest);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable final String userId) {
		return userService.deleteUser(userId);
	}
}
