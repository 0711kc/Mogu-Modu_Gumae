package com.bunsaned3thinking.mogu.heart.repository.component;

import java.util.Optional;

import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.heart.entity.HeartId;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface HeartComponentRepository {
	Optional<Post> findPostById(Long postId);

	Optional<User> findUserByUserId(String userId);

	boolean existsHeartById(HeartId heartId);

	void saveHeart(Heart heart);

	void deleteHeartById(HeartId heartId);
}
