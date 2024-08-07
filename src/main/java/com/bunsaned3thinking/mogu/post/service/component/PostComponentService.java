package com.bunsaned3thinking.mogu.post.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.SearchHistoryResponse;
import com.bunsaned3thinking.mogu.report.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.report.dto.response.ReportResponse;

public interface PostComponentService {
	ResponseEntity<PostWithDetailResponse> createPost(String userId, PostRequest postRequest,
		List<MultipartFile> multipartFileList);

	ResponseEntity<PostResponse> findPost(Long postId);

	ResponseEntity<PostWithDetailResponse> findPostWithDetail(Long postId);

	ResponseEntity<PostWithDetailResponse> updatePost(Long postId, String userId,
		UpdatePostRequest updatePostRequest, List<MultipartFile> multipartFileList);

	ResponseEntity<List<PostResponse>> searchPostByTitle(String title, String userId);

	ResponseEntity<ReportResponse> createReport(Long postId, String userId, ReportRequest reportRequest);

	ResponseEntity<List<PostResponse>> findAllReportedPost();

	ResponseEntity<List<SearchHistoryResponse>> findAllSearchHistory(String userId);

	ResponseEntity<Void> deleteSearchHistory(Long searchHistoryId, String userId);

	ResponseEntity<Void> deletePost(String userId, Long postId);

	ResponseEntity<PostResponse> closePost(Long postId, String userId);

	ResponseEntity<List<PostResponse>> findAllPosts(String userId, Long cursor);

	ResponseEntity<PostResponse> hidePost(Long postId, String userId, boolean state);
}
