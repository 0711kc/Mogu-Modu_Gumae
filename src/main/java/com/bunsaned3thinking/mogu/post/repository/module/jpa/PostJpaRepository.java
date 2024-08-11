package com.bunsaned3thinking.mogu.post.repository.module.jpa;

import java.time.LocalDate;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bunsaned3thinking.mogu.post.entity.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
	String findPageQuery =
		"select p.* from post as p left join "
			+ "(select post_id from hidden_post where user_uid = :userUid) as hp "
			+ "on p.id = hp.post_id "
			+ "where hp.post_id is null and p.is_hidden != true and p.id >= :cursor "
			+ "and ST_CONTAINS((ST_Buffer(:point, :distanceMeters)), p.location) "
			+ "order by p.post_date;";

	@Query(nativeQuery = true, value = findPageQuery)
	Slice<Post> findNextPage(Long userUid, Geometry point, short distanceMeters, Long cursor,
		PageRequest pageRequest);

	String findReportedPostsPageQuery =
		"select post.*, count(*) as prc from post join report on post.id = report.post_id "
			+ "group by report.post_id "
			+ "having (:reportsCount = prc and report.post_id > :id) "
			+ "or (:reportsCount > prc) "
			+ "order by prc desc, report.post_id asc";

	@Query(nativeQuery = true, value = findReportedPostsPageQuery)
	Slice<Post> findByIdGreaterThanEqualAndReportsIsNotEmpty(Integer reportsCount, Long id, PageRequest pageRequest);

	String findReportedPostsFirstPageQuery =
		"select post.* from post join report on post.id = report.post_id "
			+ "group by report.post_id "
			+ "order by count(*) desc, post_id asc";

	@Query(nativeQuery = true, value = findReportedPostsFirstPageQuery)
	Slice<Post> findAllFirstPageReportedPost(PageRequest pageRequest);

	@Query("select p from Post p join fetch p.hearts pl where pl.user.userId = :userId and p.id >= :id")
	List<Post> findLikedPostsByIdGreaterThanEqualAndUserId(Long id, String userId, PageRequest pageRequest);

	Slice<Post> findByIdIn(List<Long> postIds);

	List<Post> findByPurchaseDate(LocalDate purchaseDate);
}
