package com.busaned_thinking.mogu.post.service;

import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busaned_thinking.mogu.config.S3Config;
import com.busaned_thinking.mogu.exception.DeletedPostException;
import com.busaned_thinking.mogu.location.entity.Location;
import com.busaned_thinking.mogu.post.controller.dto.request.PostRequest;
import com.busaned_thinking.mogu.post.controller.dto.request.UpdatePostRequest;
import com.busaned_thinking.mogu.post.controller.dto.response.PostResponse;
import com.busaned_thinking.mogu.post.entity.Category;
import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.post.entity.PostDetail;
import com.busaned_thinking.mogu.post.entity.PostImage;
import com.busaned_thinking.mogu.post.repository.PostDetailRepository;
import com.busaned_thinking.mogu.post.repository.PostImageRepository;
import com.busaned_thinking.mogu.post.repository.PostRepository;
import com.busaned_thinking.mogu.user.entity.User;
import com.busaned_thinking.mogu.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final PostDetailRepository postDetailRepository;
	private final PostImageRepository postImageRepository;

	@Override
	public ResponseEntity<PostResponse> createPost(String userId, PostRequest postRequest, Location location,
		List<String> postImageLinks) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		PostDetail postDetail = postRequest.toDetailEntity();
		List<PostImage> postImages = createPostImages(postImageLinks, postDetail);
		postImageRepository.saveAll(postImages);
		postDetail.updatePostImages(postImages);
		Post post = postRequest.toEntity(user, location, postDetail);
		postDetail.initialize(post);
		postDetailRepository.save(postDetail);
		Post savedPost = postRepository.save(post);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(savedPost));
	}

	private List<PostImage> createPostImages(List<String> postImageLinks, PostDetail postDetail) {
		if (postImageLinks.isEmpty()) {
			return List.of(PostImage.from(S3Config.basicPostImage(), postDetail));
		}
		return postImageLinks.stream()
			.map(postImageLink -> PostImage.from(postImageLink, postDetail))
			.toList();
	}

	@Override
	public ResponseEntity<PostResponse> findPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(post));
	}

	@Override
	public ResponseEntity<PostResponse> updatePost(String userId, Long postId, UpdatePostRequest updatePostRequest,
		List<String> postImageLinks, Location location) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 수정할 수 있습니다.");
		}
		UpdatePostRequest originPost;
		try {
			originPost = UpdatePostRequest.from(post);
		} catch (DeletedPostException e) {
			throw new IllegalArgumentException("[Error] 삭제된 게시글은 수정할 수 없습니다.");
		}
		List<PostImage> postImages = post.getPostDetail().getPostImages();
		if (!postImageLinks.isEmpty()) {
			postImages = createPostImages(postImageLinks, post.getPostDetail());
		}
		copyNonNullProperties(updatePostRequest, originPost);
		update(post, originPost, location, postImages);
		postDetailRepository.save(post.getPostDetail());
		Post updatedPost = postRepository.save(post);
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(PostResponse.from(updatedPost));
	}

	private void update(Post post, UpdatePostRequest updatePostRequest, Location location, List<PostImage> postImages) {
		Category category = post.getCategory();
		LocalDateTime purchaseDate = updatePostRequest.getPurchaseDate();
		int userCount = updatePostRequest.getUserCount();
		String title = updatePostRequest.getTitle();
		int discountCost = updatePostRequest.getDiscountCost();
		int originalCost = updatePostRequest.getOriginalCost();
		if (location == null) {
			location = post.getLocation();
		}
		post.update(category, purchaseDate, userCount, title, discountCost, originalCost, location);

		String content = updatePostRequest.getContent();
		boolean shareCondition = updatePostRequest.getShareCondition();
		post.getPostDetail().update(content, shareCondition);
		post.getPostDetail().updatePostImages(postImages);
	}

	@Override
	public ResponseEntity<Void> deletePost(String userId, Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("[Error] 게시글을 찾을 수 없습니다."));
		if (!post.getUser().getUserId().equals(userId)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글만 삭제할 수 있습니다.");
		}
		postDetailRepository.deleteByPostId(post.getId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = Arrays.stream(pds)
			.map(FeatureDescriptor::getName)
			.filter(name -> src.getPropertyValue(name) == null)
			.collect(Collectors.toSet());
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
