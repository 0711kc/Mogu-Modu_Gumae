package com.bunsaned3thinking.mogu.post.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
