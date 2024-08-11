package com.bunsaned3thinking.mogu.heart.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;

public interface HeartService {
	ResponseEntity<PostResponse> likePost(Long postId, String userId);

	ResponseEntity<Void> unlikePost(Long postId, String userId);

	ResponseEntity<List<PostResponse>> findLikedPostsByUserId(String userId);
}
