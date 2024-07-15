package com.busaned_thinking.mogu.user.service;

import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
}
