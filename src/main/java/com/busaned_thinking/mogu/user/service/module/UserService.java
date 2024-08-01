package com.busaned_thinking.mogu.user.service.module;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.location.entity.ActivityArea;
import com.busaned_thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.busaned_thinking.mogu.user.controller.dto.request.UserRequest;
import com.busaned_thinking.mogu.user.controller.dto.response.UserResponse;

import lombok.NonNull;

public interface UserService {
	ResponseEntity<UserResponse> createUser(UserRequest userRequest, ActivityArea activityArea);

	ResponseEntity<UserResponse> updateProfileImage(String userId, @NonNull String profileImage);

	ResponseEntity<Void> deleteUser(String userId);

	ResponseEntity<UserResponse> findUser(String userId);

	ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest);
}
