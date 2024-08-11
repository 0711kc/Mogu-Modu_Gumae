package com.bunsaned3thinking.mogu.heart.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.heart.entity.HeartId;
import com.bunsaned3thinking.mogu.heart.repository.HeartRepository;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
	private final PostJpaRepository postRepository;
	private final UserRepository userRepository;
	private final HeartRepository heartRepository;

	@Override
	public ResponseEntity<PostResponse> likePost(Long postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Heart heart = Heart.of(post, user);
		heartRepository.save(heart);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(post));
	}

	@Override
	public ResponseEntity<Void> unlikePost(Long postId, String userId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		HeartId heartId = HeartId.of(post, user);
		heartRepository.deleteById(heartId);
		return null;
	}
}
