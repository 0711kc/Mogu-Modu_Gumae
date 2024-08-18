package com.bunsaned3thinking.mogu.user.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;
import com.bunsaned3thinking.mogu.user.entity.Manner;

public interface UserComponentService {
	ResponseEntity<UserResponse> createUser(UserRequest userRequest);

	ResponseEntity<UserResponse> findUser(String userId);

	ResponseEntity<Void> deleteUser(String userId);

	ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest,
		MultipartFile multipartFile);

	ResponseEntity<UserResponse> updateUserPassword(String userId, UpdateUserPasswordRequest updateUserPasswordRequest);

	ResponseEntity<UserResponse> setBlockUser(String userId, boolean state);

	ResponseEntity<UserResponse> updateUserManner(String senderId, String receiverId, Manner manner, Long postId);

	ResponseEntity<SavingCostResponse> findUserSavingCost(String userId);

	ResponseEntity<LevelResponse> findUserLevel(String userId);

	ResponseEntity<List<UserResponse>> findAllUser(Long cursor);
}
