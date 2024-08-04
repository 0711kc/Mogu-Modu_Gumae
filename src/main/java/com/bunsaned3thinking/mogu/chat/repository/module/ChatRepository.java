package com.bunsaned3thinking.mogu.chat.repository.module;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	@Query("select c from Chat c left join fetch c.post p left join fetch p.user u where u.userId = :userId")
	List<Chat> findByUserId(String userId);

	boolean existsByPostId(Long postId);
}
