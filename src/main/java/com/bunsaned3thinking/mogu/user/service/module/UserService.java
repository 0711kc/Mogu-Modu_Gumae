package com.bunsaned3thinking.mogu.user.service.module;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserDetailResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;

import lombok.NonNull;

public interface UserService {
	ResponseEntity<UserDetailResponse> createUser(UserRequest userRequest);

	ResponseEntity<UserDetailResponse> updateProfileImage(String userId, @NonNull String profileImage);

	ResponseEntity<Void> deleteUser(String userId);

	ResponseEntity<UserDetailResponse> findUser(String userId);

	ResponseEntity<UserDetailResponse> updateUser(String userId, UpdateUserRequest updateUserRequest);

	ResponseEntity<UserDetailResponse> updateUser(String userId, String profileImageName,
		UpdateUserRequest updateUserRequest);

	boolean checkUser(String userId);

	ResponseEntity<UserDetailResponse> updatePassword(String userId,
		UpdateUserPasswordRequest updateUserPasswordRequest);

	ResponseEntity<UserDetailResponse> setBlockUser(String userId, boolean state);

	ResponseEntity<UserDetailResponse> updateUserManner(String userId, Slice<Review> reviews);

	ResponseEntity<SavingCostResponse> findUserSavingCost(String userId);

	void updateUserLevel(Long postId, RecruitState recruitState);

	ResponseEntity<LevelResponse> findUserLevel(String userId);

	String findImageName(String userId);

	ResponseEntity<List<UserDetailResponse>> findAllUser(Long cursor);

	ResponseEntity<UserResponse> findOtherUser(String userId);
}
