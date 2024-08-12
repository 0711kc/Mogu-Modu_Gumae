package com.bunsaned3thinking.mogu.review.service;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.review.entity.Review;
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
	public void createReview(final String userId, final Manner manner) {
		User user = reviewComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		Review review = Review.of(user, manner);
		reviewComponentRepository.saveReview(review);
	}

	@Override
	public Slice<Review> findByUserId(final String userId) {
		User user = reviewComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		return reviewComponentRepository.findReviewByUserUid(user.getUid());
	}
}
