package com.busaned_thinking.mogu.post.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;
import com.busaned_thinking.mogu.post.controller.dto.response.PostWithDetailResponse;

public interface PostComponentService {
	ResponseEntity<PostWithDetailResponse> createPost(String userId, PostRequest postRequest,
		List<MultipartFile> multipartFileList);

	ResponseEntity<PostResponse> findPost(Long postId);

	ResponseEntity<PostWithDetailResponse> findPostWithDetail(Long postId);

	ResponseEntity<PostWithDetailResponse> updatePost(Long postId, String userId,
		UpdatePostRequest updatePostRequest, List<MultipartFile> multipartFileList);

	ResponseEntity<Void> deletePost(String userId, Long postId);
}
