package com.bunsaned3thinking.mogu.ask.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.ask.controller.dto.response.AskResponse;

public interface AskComponentService {
	ResponseEntity<AskResponse> createAsk(String userId, Long postId);

	ResponseEntity<List<AskResponse>> findAskByUser(String userId);

	ResponseEntity<List<AskResponse>> findAskByPost(Long postId);

	ResponseEntity<AskResponse> updateAsk(String userId, Long postId, boolean state);
}
