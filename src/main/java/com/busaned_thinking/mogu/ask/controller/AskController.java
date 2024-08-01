package com.busaned_thinking.mogu.ask.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.busaned_thinking.mogu.ask.controller.dto.response.AskResponse;
import com.busaned_thinking.mogu.ask.service.AskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ask")
@RequiredArgsConstructor
public class AskController {
	private final AskService askService;

	@PostMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<AskResponse> createAsk(@PathVariable final String userId, @PathVariable final Long postId) {
		return askService.createAsk(userId, postId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AskResponse>> findAskByUser(@PathVariable final String userId) {
		return askService.findAskByUser(userId);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<List<AskResponse>> findAskByPost(@PathVariable final Long postId) {
		return askService.findAskByPost(postId);
	}

	@PatchMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<AskResponse> setAskState(@PathVariable final String userId,
		@PathVariable final Long postId, @RequestPart(name = "state") boolean state) {
		return askService.setAskState(userId, postId, state);
	}
}
