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

	@Query("select p from Post p where p.id <= :cursor and p.isHidden = false order by p.postDate desc")
	Slice<Post> findNextPage(Long cursor, PageRequest pageRequest);
}
