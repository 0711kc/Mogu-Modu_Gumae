package com.bunsaned3thinking.mogu.post.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.HiddenPostId;

public interface HiddenPostRepository extends JpaRepository<HiddenPost, HiddenPostId> {
}
