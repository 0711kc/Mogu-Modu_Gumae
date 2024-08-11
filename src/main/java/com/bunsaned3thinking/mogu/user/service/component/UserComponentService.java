package com.bunsaned3thinking.mogu.user.service.component;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;

public interface UserComponentService {
	ResponseEntity<UserResponse> createUser(UserRequest userRequest);

	ResponseEntity<UserResponse> findUser(String userId);

	ResponseEntity<Void> deleteUser(String userId);

	ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest,
		MultipartFile multipartFile);

	ResponseEntity<UserResponse> updateUserPassword(String userId, UpdateUserPasswordRequest updateUserPasswordRequest);

	ResponseEntity<UserResponse> setBlockUser(String userId, boolean state);
}
