package com.bunsaned3thinking.mogu.heart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.heart.entity.Heart;
import com.bunsaned3thinking.mogu.heart.entity.HeartId;

public interface HeartRepository extends JpaRepository<Heart, HeartId> {
}
