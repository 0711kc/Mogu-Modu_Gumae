package com.bunsaned3thinking.mogu.post.service.module;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.location.entity.Location;
import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.ReportResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.SearchHistoryResponse;

public interface PostService {

	ResponseEntity<List<PostResponse>> searchPostsByTitle(String keyword, String userId);

	ResponseEntity<ReportResponse> createReport(Long postId, String userId, ReportRequest reportRequest);

	ResponseEntity<PostWithDetailResponse> createPost(String memberId, PostRequest postRequest, Location location,
		List<String> postImageLinks);

	ResponseEntity<PostResponse> findPost(Long id);

	ResponseEntity<PostWithDetailResponse> findPostWithDetail(Long id);

	ResponseEntity<PostWithDetailResponse> updatePost(String userId, Long postId, UpdatePostRequest updatePostRequest,
		List<String> postImageLinks, Location location);

	ResponseEntity<List<PostResponse>> findAllReportedPost();

	ResponseEntity<List<SearchHistoryResponse>> findAllSearchHistoryByUserId(String userId);

	ResponseEntity<Void> deletePost(String userId, Long postId);

	ResponseEntity<Void> deleteSearchHistory(Long searchHistoryId, String userId);
}
