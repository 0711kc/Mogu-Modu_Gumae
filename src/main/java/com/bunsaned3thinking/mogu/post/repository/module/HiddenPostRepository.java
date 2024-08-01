package com.bunsaned3thinking.mogu.post.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.post.entity.HiddenPost;

public interface HiddenPostRepository extends JpaRepository<HiddenPost, Long> {
}
