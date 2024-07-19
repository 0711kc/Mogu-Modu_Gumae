package com.busaned_thinking.mogu.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.post.entity.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}