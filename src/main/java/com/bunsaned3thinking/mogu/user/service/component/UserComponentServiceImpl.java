package com.bunsaned3thinking.mogu.user.service.component;

import java.util.List;

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
import com.bunsaned3thinking.mogu.user.controller.dto.response.LevelResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.SavingCostResponse;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserDetailResponse;
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
	public ResponseEntity<UserDetailResponse> createUser(UserRequest userRequest) {
		return userService.createUser(userRequest);
	}

	@Override
	public ResponseEntity<UserDetailResponse> findUser(String userId) {
		return userService.findUser(userId);
	}

	@Override
	public ResponseEntity<List<UserDetailResponse>> findAllUser(Long cursor) {
		return userService.findAllUser(cursor);
	}

	@Override
	public ResponseEntity<UserResponse> findOtherUser(String userId) {
		return userService.findOtherUser(userId);
	}

	@Override
	public ResponseEntity<Void> deleteUser(String userId) {
		String imageName = userService.findImageName(userId);
		ResponseEntity<Void> response = userService.deleteUser(userId);
		imageService.delete(imageName);
		return response;
	}

	@Override
	public ResponseEntity<UserDetailResponse> updateUser(String userId, UpdateUserRequest updateUserRequest,
		MultipartFile multipartFile) {
		if (multipartFile != null & updateUserRequest != null) {
			String imageName = imageService.upload(multipartFile);
			try {
				ResponseEntity<UserDetailResponse> response = userService.updateUser(userId, imageName,
					updateUserRequest);
				imageService.delete(userService.findImageName(userId));
				return response;
			} catch (RuntimeException e) {
				imageService.delete(imageName);
				throw e;
			}
		} else if (multipartFile != null) {
			String imageName = imageService.upload(multipartFile);
			try {
				ResponseEntity<UserDetailResponse> response = userService.updateProfileImage(userId, imageName);
				imageService.delete(userService.findImageName(userId));
				return response;
			} catch (RuntimeException e) {
				imageService.delete(imageName);
				throw e;
			}
		} else if (updateUserRequest != null) {
			return userService.updateUser(userId, updateUserRequest);
		}
		throw new IllegalArgumentException("[Error] 수정할 데이터를 전달받지 못했습니다.");
	}

	@Override
	public ResponseEntity<UserDetailResponse> updateUserPassword(String userId,
		UpdateUserPasswordRequest updateUserPasswordRequest) {
		return userService.updatePassword(userId, updateUserPasswordRequest);
	}

	@Override
	public ResponseEntity<UserDetailResponse> setBlockUser(String userId, boolean state) {
		return userService.setBlockUser(userId, state);
	}

	@Override
	public ResponseEntity<UserDetailResponse> updateUserManner(String senderId, String receiverId, Manner manner,
		Long postId) {
		reviewService.createReview(senderId, receiverId, manner, postId);
		Slice<Review> reviews = reviewService.findByReceiverId(receiverId);
		return userService.updateUserManner(receiverId, reviews);
	}

	@Override
	public ResponseEntity<SavingCostResponse> findUserSavingCost(String userId) {
		return userService.findUserSavingCost(userId);
	}

	@Override
	public ResponseEntity<LevelResponse> findUserLevel(String userId) {
		return userService.findUserLevel(userId);
	}
}
