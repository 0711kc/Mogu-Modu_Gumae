package com.busaned_thinking.mogu.user.controller;

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

import com.busaned_thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.busaned_thinking.mogu.user.controller.dto.request.UserRequest;
import com.busaned_thinking.mogu.user.controller.dto.response.UserResponse;
import com.busaned_thinking.mogu.user.service.component.UserComponentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserComponentService userComponentService;
	// private final UserService userService;
	// private final ActivityAreaService activityAreaService;
	// private final ImageService imageService;

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid final UserRequest userRequest) {
		// ActivityArea activityArea = activityAreaService.create(userRequest.getLongitude(), userRequest.getLatitude());
		// return userService.createUser(userRequest, activityArea);
		return userComponentService.createUser(userRequest);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> findUser(@PathVariable final String userId) {
		return userComponentService.findUser(userId);
		// return userService.findUser(userId);
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable final String userId,
		@RequestBody @Valid UpdateUserRequest updateUserRequest) {
		// return userService.updateUser(userId, updateUserRequest);
		return userComponentService.updateUser(userId, updateUserRequest);
	}

	@PatchMapping("/{userId}/image")
	public ResponseEntity<UserResponse> updateProfileImage(@PathVariable final String userId,
		@RequestPart(name = "image") MultipartFile multipartFile) {
		// String imageLink = imageService.upload(multipartFile);
		// return userService.updateProfileImage(userId, imageLink);
		return userComponentService.updateProfileImage(userId, multipartFile);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable final String userId) {
		// return userService.deleteUser(userId);
		return userComponentService.deleteUser(userId);
	}
}
