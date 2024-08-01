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
import com.busaned_thinking.mogu.ask.service.component.AskComponentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ask")
@RequiredArgsConstructor
public class AskController {
	private final AskComponentService askComponentService;

	@PostMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<AskResponse> createAsk(@PathVariable final String userId, @PathVariable final Long postId) {
		return askComponentService.createAsk(userId, postId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AskResponse>> findAskByUser(@PathVariable final String userId) {
		return askComponentService.findAskByUser(userId);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<List<AskResponse>> findAskByPost(@PathVariable final Long postId) {
		return askComponentService.findAskByPost(postId);
	}

	@PatchMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<AskResponse> updateAskState(@PathVariable final String userId,
		@PathVariable final Long postId, @RequestPart(name = "state") Boolean state) {
		return askComponentService.updateAsk(userId, postId, state);
	}
}
