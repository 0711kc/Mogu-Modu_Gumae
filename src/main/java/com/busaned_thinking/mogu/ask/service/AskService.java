package com.busaned_thinking.mogu.ask.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.busaned_thinking.mogu.ask.controller.dto.response.AskResponse;

public interface AskService {
	ResponseEntity<AskResponse> createAsk(String userId, Long postId);

	ResponseEntity<List<AskResponse>> findAskByUser(String userId);

	ResponseEntity<List<AskResponse>> findAskByPost(Long postId);

	ResponseEntity<AskResponse> setAskState(String userId, Long postId, Boolean state);
}
