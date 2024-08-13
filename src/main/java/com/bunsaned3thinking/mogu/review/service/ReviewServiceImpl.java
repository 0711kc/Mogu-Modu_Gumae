package com.bunsaned3thinking.mogu.review.service;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.chat.entity.ChatUser;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.review.entity.ReviewId;
import com.bunsaned3thinking.mogu.review.repository.component.ReviewComponentRepository;
import com.bunsaned3thinking.mogu.user.entity.Manner;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
	private final ReviewComponentRepository reviewComponentRepository;

	@Override
	public void createReview(final String senderId, final String receiverId, final Manner manner, final Long postId) {
		if (senderId.equals(receiverId)) {
			throw new IllegalArgumentException("[Error] 자신은 평가할 수 없습니다.");
		}
		Post post = reviewComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시글을 찾을 수 없습니다."));
		User sender = reviewComponentRepository.findUserByUserId(senderId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		User receiver = reviewComponentRepository.findUserByUserId(receiverId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		if (post.getRecruitState().equals(RecruitState.RECRUITING)) {
			throw new IllegalArgumentException("[Error] 모집중인 게시글의 사용자는 평가할 수 없습니다.");
		}
		long userCount = post.getChat().getUsers().stream()
			.map(ChatUser::getUser)
			.filter(user -> user.getUserId().equals(senderId) | user.getUserId().equals(receiverId))
			.count();
		if (userCount < 2) {
			throw new IllegalArgumentException("[Error] 해당 채팅에 참여하지 않은 사용자입니다.");
		}
		boolean isExistReview = reviewComponentRepository.existsById(
			ReviewId.of(sender.getUid(), receiver.getUid(), post.getId()));
		if (isExistReview) {
			throw new IllegalArgumentException("[Error] 이미 평가를 남긴 사용자입니다.");
		}
		Review review = Review.of(sender, receiver, manner, post);
		reviewComponentRepository.saveReview(review);
	}

	@Override
	public Slice<Review> findByReceiverId(final String receiverId) {
		User receiver = reviewComponentRepository.findUserByUserId(receiverId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		return reviewComponentRepository.findReviewByReceiverUid(receiver.getUid());
	}
}
