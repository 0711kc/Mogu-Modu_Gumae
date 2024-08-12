package com.bunsaned3thinking.mogu.review.repository.component;

import java.util.Optional;

import org.springframework.data.domain.Slice;

import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface ReviewComponentRepository {
	Optional<User> findUserByUserId(String userId);

	void saveReview(Review review);

	Slice<Review> findReviewByUserUid(Long userUid);
}
