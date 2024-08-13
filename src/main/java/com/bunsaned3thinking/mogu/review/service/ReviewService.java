package com.bunsaned3thinking.mogu.review.service;

import org.springframework.data.domain.Slice;

import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.user.entity.Manner;

public interface ReviewService {
	void createReview(String senderId, String receiverId, Manner manner, Long postId);

	Slice<Review> findByReceiverId(String userId);
}
