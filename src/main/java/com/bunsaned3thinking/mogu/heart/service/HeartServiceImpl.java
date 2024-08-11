package com.bunsaned3thinking.mogu.heart.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.heart.entity.HeartId;
import com.bunsaned3thinking.mogu.heart.repository.component.HeartComponentRepository;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {
	private final HeartComponentRepository heartComponentrepository;

	@Override
	public ResponseEntity<PostResponse> likePost(Long postId, String userId) {
		Post post = heartComponentrepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		User user = heartComponentrepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		if (post.getUser().equals(user)) {
			throw new IllegalArgumentException("[Error] 자신의 글은 좋아요 표시를 할 수 없습니다.");
		}
		HeartId heartId = HeartId.of(post, user);
		boolean isExistHeart = heartComponentrepository.existsHeartById(heartId);
		if (isExistHeart) {
			throw new IllegalArgumentException("[Error] 이미 좋아요 표시한 게시글입니다.");
		}
		Heart heart = Heart.of(post, user);
		heartComponentrepository.saveHeart(heart);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(post));
	}

	@Override
	public ResponseEntity<Void> unlikePost(Long postId, String userId) {
		Post post = heartComponentrepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		User user = heartComponentrepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		HeartId heartId = HeartId.of(post, user);
		boolean isExistHeart = heartComponentrepository.existsHeartById(heartId);
		if (!isExistHeart) {
			throw new IllegalArgumentException("[Error] 좋아요 표시를 하지 않은 게시글입니다.");
		}
		heartComponentrepository.deleteHeartById(heartId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
