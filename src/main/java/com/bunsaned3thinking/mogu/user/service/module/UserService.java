package com.bunsaned3thinking.mogu.user.service.module;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;

import lombok.NonNull;

public interface UserService {
	ResponseEntity<UserResponse> createUser(UserRequest userRequest);

	ResponseEntity<UserResponse> updateProfileImage(String userId, @NonNull String profileImage);

	ResponseEntity<Void> deleteUser(String userId);

	ResponseEntity<UserResponse> findUser(String userId);

	ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest);

	boolean checkUser(String userId);

	ResponseEntity<UserResponse> updatePassword(String userId, UpdateUserPasswordRequest updateUserPasswordRequest);

	ResponseEntity<UserResponse> setBlockUser(String userId, boolean state);

	ResponseEntity<UserResponse> updateUserManner(String userId, Slice<Review> reviews);

	ResponseEntity<SavingCostResponse> findUserSavingCost(String userId);

	void updateUserLevel(Long postId, RecruitState recruitState);

	ResponseEntity<LevelResponse> findUserLevel(String userId);
}
