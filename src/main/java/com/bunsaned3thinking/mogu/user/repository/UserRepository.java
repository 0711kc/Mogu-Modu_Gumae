package com.bunsaned3thinking.mogu.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);
}
