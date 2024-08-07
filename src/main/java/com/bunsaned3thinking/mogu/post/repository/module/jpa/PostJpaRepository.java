package com.bunsaned3thinking.mogu.post.repository.module.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.post.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
	List<Post> findByPurchaseDate(LocalDate purchaseDate);

	@Query("select p from Post p left join fetch p.hiddenPosts hp "
		+ "where (hp is null or hp.user.uid != :userUid) and p.id <= :cursor and p.isHidden != true "
		+ "order by p.postDate DESC")
	Slice<Post> findNextPage(Long userUid, Long cursor, PageRequest pageRequest);
}
