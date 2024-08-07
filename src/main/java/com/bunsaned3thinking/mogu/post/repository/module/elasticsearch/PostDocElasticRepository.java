package com.bunsaned3thinking.mogu.post.repository.module.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bunsaned3thinking.mogu.post.entity.PostDoc;

public interface PostDocElasticRepository extends ElasticsearchRepository<PostDoc, Long> {
	List<PostDoc> findByTitleContaining(String title);
}
