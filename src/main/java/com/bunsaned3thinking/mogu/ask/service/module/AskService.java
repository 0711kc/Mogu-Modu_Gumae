package com.bunsaned3thinking.mogu.ask.service.module;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.ask.controller.dto.response.AskResponse;

public interface AskService {
	ResponseEntity<AskResponse> createAsk(String userId, Long postId);

	ResponseEntity<List<AskResponse>> findAskByUser(String userId);

	ResponseEntity<List<AskResponse>> findAskByPost(Long postId);

	ResponseEntity<AskResponse> updateAskState(String userId, Long postId, Boolean state);
}
