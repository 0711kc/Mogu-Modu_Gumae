package com.busaned_thinking.mogu.ask.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.busaned_thinking.mogu.ask.controller.dto.response.AskResponse;
import com.busaned_thinking.mogu.ask.entity.Ask;
import com.busaned_thinking.mogu.ask.entity.AskState;
import com.busaned_thinking.mogu.ask.repository.AskRepository;
import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.post.repository.PostRepository;
import com.busaned_thinking.mogu.user.entity.User;
import com.busaned_thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AskServiceImpl implements AskService {
	private final AskRepository askRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Override
	public ResponseEntity<AskResponse> createAsk(String userId, Long postId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (post.getUser().getUid().equals(user.getUid())) {
			throw new IllegalArgumentException("[Error] 자기 자신의 글은 참여 신청할 수 없습니다.");
		}
		boolean isExistAsk = askRepository.existsByUserUidAndPostId(user.getUid(), postId);
		if (isExistAsk) {
			throw new IllegalArgumentException("[Error] 이미 참여 신청이 완료된 게시글입니다.");
		}
		Ask ask = Ask.from(post, user);
		Ask savedAsk = askRepository.save(ask);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(AskResponse.from(savedAsk));
	}

	@Override
	public ResponseEntity<List<AskResponse>> findAskByUser(String userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		List<Ask> asks = askRepository.findByUserUid(user.getUid());
		List<AskResponse> askResponses = asks.stream()
			.map(AskResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(askResponses);
	}

	@Override
	public ResponseEntity<List<AskResponse>> findAskByPost(Long postId) {
		List<Ask> asks = askRepository.findByPostId(postId);
		List<AskResponse> askResponses = asks.stream()
			.map(AskResponse::from)
			.toList();
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(askResponses);
	}

	@Override
	public ResponseEntity<AskResponse> setAskState(String userId, Long postId, Boolean state) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Ask ask = askRepository.findByUserUidAndPostId(user.getUid(), postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 신청 정보를 찾을 수 없습니다."));
		AskState askState = AskState.findByState(state);
		ask.update(askState);
		Ask savedAsk = askRepository.save(ask);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(AskResponse.from(savedAsk));
	}
}
