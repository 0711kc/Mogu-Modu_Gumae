package com.bunsaned3thinking.mogu.post.service.component;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bunsaned3thinking.mogu.image.service.ImageService;
import com.bunsaned3thinking.mogu.location.entity.Location;
import com.bunsaned3thinking.mogu.location.service.LocationService;
import com.bunsaned3thinking.mogu.post.controller.dto.request.PostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostResponse;
import com.bunsaned3thinking.mogu.post.controller.dto.response.PostWithDetailResponse;
import com.bunsaned3thinking.mogu.post.service.module.PostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostComponentServiceImpl implements PostComponentService {
	private final PostService postService;
	private final LocationService locationService;
	private final ImageService imageService;

	@Override
	public ResponseEntity<PostWithDetailResponse> createPost(final String userId, final PostRequest postRequest,
		final List<MultipartFile> multipartFileList) {
		List<String> imageLinks = imageService.uploadAll(multipartFileList);
		Location location = locationService.createLocation(postRequest.getLongitude(), postRequest.getLatitude());
		return postService.createPost(userId, postRequest, location, imageLinks);
	}

	@Override
	public ResponseEntity<PostResponse> findPost(final Long postId) {
		return postService.findPost(postId);
	}

	@Override
	public ResponseEntity<PostWithDetailResponse> findPostWithDetail(final Long postId) {
		return postService.findPostWithDetail(postId);
	}

	@Override
	public ResponseEntity<PostWithDetailResponse> updatePost(final Long postId, final String userId,
		final UpdatePostRequest updatePostRequest, final List<MultipartFile> multipartFileList) {
		List<String> imageLinks = imageService.uploadAll(multipartFileList);
		Location location = locationService.createLocation(updatePostRequest.getLongitude(),
			updatePostRequest.getLatitude());
		return postService.updatePost(userId, postId, updatePostRequest, imageLinks, location);
	}

	@Override
	public ResponseEntity<Void> deletePost(final String userId, final Long postId) {
		return postService.deletePost(userId, postId);
	}

	@Override
	public ResponseEntity<List<PostResponse>> searchPostByTitle(String title) {
		return postService.searchPostsByTitle(title);
	}
}
