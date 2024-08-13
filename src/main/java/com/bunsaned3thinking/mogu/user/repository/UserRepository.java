package com.bunsaned3thinking.mogu.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.RecruitState;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUserId(String userId);

	boolean existsByUserId(String userId);

	@Query("select p from Post p join fetch p.user u where u.uid = :uid and p.recruitState = :recruitState")
	List<Post> findPostsByUserUidAndRecruitState(Long uid, RecruitState recruitState);
}
