package com.bunsaned3thinking.mogu.review.repository.module;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.review.entity.ReviewId;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
	Slice<Review> findByReceiverUid(Long receiverUid);
}
