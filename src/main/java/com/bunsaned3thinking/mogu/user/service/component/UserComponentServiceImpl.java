package com.bunsaned3thinking.mogu.user.service.component;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.image.service.ImageService;
import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.review.service.ReviewService;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserPasswordRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;
import com.bunsaned3thinking.mogu.user.entity.Manner;
import com.bunsaned3thinking.mogu.user.service.module.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserComponentServiceImpl implements UserComponentService {
	private final UserService userService;
	private final ImageService imageService;
	private final ReviewService reviewService;

	@Override
	public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
		return userService.createUser(userRequest);
	}

	@Override
	public ResponseEntity<UserResponse> findUser(String userId) {
		return userService.findUser(userId);
	}

	@Override
	public ResponseEntity<Void> deleteUser(String userId) {
		return userService.deleteUser(userId);
	}

	@Override
	public ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest,
		MultipartFile multipartFile) {
		ResponseEntity<UserResponse> response = null;
		if (multipartFile != null) {
			String imageLink = imageService.upload(multipartFile);
			response = userService.updateProfileImage(userId, imageLink);
		}
		if (updateUserRequest != null) {
			response = userService.updateUser(userId, updateUserRequest);
		}
		if (response == null) {
			throw new IllegalArgumentException("[Error] 수정할 데이터를 전달받지 못했습니다.");
		}
		return response;
	}

	@Override
	public ResponseEntity<UserResponse> updateUserPassword(String userId,
		UpdateUserPasswordRequest updateUserPasswordRequest) {
		return userService.updatePassword(userId, updateUserPasswordRequest);
	}

	@Override
	public ResponseEntity<UserResponse> setBlockUser(String userId, boolean state) {
		return userService.setBlockUser(userId, state);
	}

	@Override
	public ResponseEntity<UserResponse> updateUserManner(String senderId, String receiverId, Manner manner,
		Long postId) {
		reviewService.createReview(senderId, receiverId, manner, postId);
		Slice<Review> reviews = reviewService.findByReceiverId(receiverId);
		return userService.updateUserManner(receiverId, reviews);
	}

	@Override
	public ResponseEntity<SavingCostResponse> findUserSavingCost(String userId) {
		return userService.findUserSavingCost(userId);
	}
}
