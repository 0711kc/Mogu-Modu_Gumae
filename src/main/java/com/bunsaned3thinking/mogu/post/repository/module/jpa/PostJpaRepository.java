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

	String findPageQuery =
		"select p.* from post as p left join "
			+ "(select post_id from hidden_post where user_uid = :userUid) as hp "
			+ "on p.id = hp.post_id "
			+ "where hp.post_id is null and p.is_hidden != true and p.id <= :cursor "
			+ "and ST_CONTAINS((ST_Buffer(ST_SRID(POINT(:latitude, :longitude), 4326), :distanceMeters)), ST_POINTFROMTEXT(p.location, 4326)) "
			+ "order by p.post_date desc;";

	@Query(nativeQuery = true, value = findPageQuery)
	Slice<Post> findNextPage(Long userUid, double longitude, double latitude, short distanceMeters, Long cursor,
		PageRequest pageRequest);
}
