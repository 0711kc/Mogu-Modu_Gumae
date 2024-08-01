package com.bunsaned3thinking.mogu.post.service.module;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.location.entity.Location;
import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;

public interface PostService {

	ResponseEntity<Void> deletePost(String userId, Long postId);

	ResponseEntity<PostWithDetailResponse> createPost(String memberId, PostRequest postRequest, Location location,
		List<String> postImageLinks);

	ResponseEntity<PostResponse> findPost(Long id);

	ResponseEntity<PostWithDetailResponse> findPostWithDetail(Long id);

	ResponseEntity<PostWithDetailResponse> updatePost(String userId, Long postId, UpdatePostRequest updatePostRequest,
		List<String> postImageLinks, Location location);
}
