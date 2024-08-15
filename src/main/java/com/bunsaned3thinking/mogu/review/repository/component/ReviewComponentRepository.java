package com.bunsaned3thinking.mogu.review.repository.component;

import java.util.Optional;

import org.springframework.data.domain.Slice;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.review.entity.ReviewId;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface ReviewComponentRepository {
	Optional<User> findUserByUserId(String userId);

	void saveReview(Review review);

	Slice<Review> findReviewByReceiverUid(Long receiverUid);

	Optional<Post> findPostById(Long postId);

	boolean existsById(ReviewId of);
}
