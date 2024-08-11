package com.bunsaned3thinking.mogu.heart.repository.component;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.heart.entity.HeartId;
import com.bunsaned3thinking.mogu.heart.repository.module.HeartRepository;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HeartComponentRepositoryImpl implements HeartComponentRepository {
	private final PostJpaRepository postRepository;
	private final UserRepository userRepository;
	private final HeartRepository heartRepository;

	@Override
	public Optional<Post> findPostById(Long postId) {
		return postRepository.findById(postId);
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public boolean existsHeartById(HeartId heartId) {
		return heartRepository.existsById(heartId);
	}

	@Override
	public void saveHeart(Heart heart) {
		heartRepository.save(heart);
	}

	@Override
	public void deleteHeartById(HeartId heartId) {
		heartRepository.deleteById(heartId);
	}
}
