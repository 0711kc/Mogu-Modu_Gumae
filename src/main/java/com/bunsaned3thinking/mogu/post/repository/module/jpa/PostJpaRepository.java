package com.bunsaned3thinking.mogu.post.repository.module.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.post.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
