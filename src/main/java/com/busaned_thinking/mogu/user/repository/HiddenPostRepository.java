package com.busaned_thinking.mogu.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.user.entity.HiddenPost;

public interface HiddenPostRepository extends JpaRepository<HiddenPost, Long> {
}
