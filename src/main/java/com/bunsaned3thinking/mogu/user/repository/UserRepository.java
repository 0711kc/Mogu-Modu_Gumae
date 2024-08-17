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

	@Query("select p from Post p join fetch p.user u where u.userId = :userId and p.recruitState = :recruitState")
	List<Post> findPostsByUserIdAndRecruitState(String userId, RecruitState recruitState);

	@Query("select u from User u join fetch u.posts p where p.id = :postId")
	List<User> findUserByPostId(Long postId);

	@Query(nativeQuery = true, value = """
		select count(*) from chat_user as cu
		join (select user.uid from user where user.uid = :uid) as u
			on cu.user_uid = u.uid
		join (select post.id from post where post.recruit_state = :recruitState) as p
		on cu.chat_id = p.id
		""")
	int countByUserUidAndRecruitState(Long uid, RecruitState recruitState);
}
