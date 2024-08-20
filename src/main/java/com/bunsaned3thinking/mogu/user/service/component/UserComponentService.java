package com.bunsaned3thinking.mogu.user.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserDetailResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;
import com.bunsaned3thinking.mogu.user.entity.Manner;

public interface UserComponentService {
	ResponseEntity<UserDetailResponse> createUser(UserRequest userRequest);

	ResponseEntity<UserDetailResponse> findUser(String userId);

	ResponseEntity<Void> deleteUser(String userId);

	ResponseEntity<UserDetailResponse> updateUser(String userId, UpdateUserRequest updateUserRequest);

	ResponseEntity<UserDetailResponse> updateUserWithProfile(String userId, UpdateUserRequest updateUserRequest,
		MultipartFile multipartFile);

	ResponseEntity<UserDetailResponse> updateUserPassword(String userId,
		UpdateUserPasswordRequest updateUserPasswordRequest);

	ResponseEntity<UserDetailResponse> setBlockUser(String userId, boolean state);

	ResponseEntity<UserDetailResponse> updateUserManner(String senderId, String receiverId, Manner manner, Long postId);

	ResponseEntity<SavingCostResponse> findUserSavingCost(String userId);

	ResponseEntity<LevelResponse> findUserLevel(String userId);

	ResponseEntity<List<UserDetailResponse>> findAllUser(Long cursor);

	ResponseEntity<UserResponse> findOtherUser(String userId);
}
