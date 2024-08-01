package com.bunsaned3thinking.mogu.user.service.component;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.image.service.ImageService;
import com.bunsaned3thinking.mogu.location.entity.ActivityArea;
import com.bunsaned3thinking.mogu.location.service.ActivityAreaService;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.request.UserRequest;
import com.bunsaned3thinking.mogu.user.controller.dto.response.UserResponse;
import com.bunsaned3thinking.mogu.user.service.module.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserComponentServiceImpl implements UserComponentService {
	private final UserService userService;
	private final ActivityAreaService activityAreaService;
	private final ImageService imageService;

	@Override
	public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
		ActivityArea activityArea = activityAreaService.create(userRequest.getLongitude(), userRequest.getLatitude());
		return userService.createUser(userRequest, activityArea);
	}

	@Override
	public ResponseEntity<UserResponse> findUser(String userId) {
		return userService.findUser(userId);
	}

	@Override
	public ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest) {
		return userService.updateUser(userId, updateUserRequest);
	}

	@Override
	public ResponseEntity<UserResponse> updateProfileImage(String userId, MultipartFile multipartFile) {
		String imageLink = imageService.upload(multipartFile);
		return userService.updateProfileImage(userId, imageLink);
	}

	@Override
	public ResponseEntity<Void> deleteUser(String userId) {
		return userService.deleteUser(userId);
	}
}
