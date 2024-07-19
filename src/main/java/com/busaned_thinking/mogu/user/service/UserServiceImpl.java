package com.busaned_thinking.mogu.user.service;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.user.controller.dto.request.UpdateUserRequest;
import com.busaned_thinking.mogu.user.controller.dto.request.UserRequest;
import com.busaned_thinking.mogu.user.controller.dto.response.UserResponse;
import com.busaned_thinking.mogu.user.entity.User;
import com.busaned_thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
		User user = userRequest.toEntity();
		User savedUser = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(UserResponse.from(savedUser));
	}

	@Override
	public ResponseEntity<UserResponse> findUser(String userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(UserResponse.from(user));
	}

	@Override
	public ResponseEntity<UserResponse> updateUser(String userId, UpdateUserRequest updateUserRequest) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		UpdateUserRequest originUser = UpdateUserRequest.from(user);
		copyNonNullProperties(updateUserRequest, originUser);
		update(user, originUser);
		User updatedUser = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(UserResponse.from(updatedUser));
	}

	private void update(User user, UpdateUserRequest updateUserRequest) {
		String name = updateUserRequest.getName();
		user.update(name);
	}

	@Override
	public ResponseEntity<Void> deleteUser(String userId) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		userRepository.deleteById(user.getUid());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = Arrays.stream(pds)
			.map(FeatureDescriptor::getName)
			.filter(name -> src.getPropertyValue(name) == null)
			.collect(Collectors.toSet());
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
