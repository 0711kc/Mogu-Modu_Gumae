package com.bunsaned3thinking.mogu.review.repository.component;

import java.util.Optional;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.review.entity.Review;
import com.bunsaned3thinking.mogu.review.entity.ReviewId;
import com.bunsaned3thinking.mogu.review.repository.module.ReviewRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewComponentRepositoryImpl implements ReviewComponentRepository {
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final PostJpaRepository postJpaRepository;

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public void saveReview(Review review) {
		reviewRepository.save(review);
	}

	@Override
	public Slice<Review> findReviewByReceiverUid(Long receiverUid) {
		return reviewRepository.findByReceiverUid(receiverUid);
	}

	@Override
	public Optional<Post> findPostById(Long postId) {
		return postJpaRepository.findById(postId);
	}

	@Override
	public boolean existsById(ReviewId reviewId) {
		return reviewRepository.existsById(reviewId);
	}
}
