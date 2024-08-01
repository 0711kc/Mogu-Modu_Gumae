package com.busaned_thinking.mogu.post.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.post.entity.PostDetail;

public interface PostDetailRepository extends JpaRepository<PostDetail, Long> {
	void deleteByPostId(Long id);
}
