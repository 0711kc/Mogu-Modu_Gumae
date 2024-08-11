package com.bunsaned3thinking.mogu.heart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.heart.entity.HeartId;
import com.bunsaned3thinking.mogu.post.entity.Post;

public interface HeartRepository extends JpaRepository<Heart, HeartId> {
	@Query("select p from Post p join fetch p.hearts pl where pl.user.userId = :userId")
	List<Post> findAllLikedPostsByUserId(String userId);
}
