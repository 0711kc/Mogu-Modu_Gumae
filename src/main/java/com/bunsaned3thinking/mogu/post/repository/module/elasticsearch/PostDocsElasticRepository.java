package com.bunsaned3thinking.mogu.post.repository.module.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bunsaned3thinking.mogu.post.entity.PostDocs;

public interface PostDocsElasticRepository extends ElasticsearchRepository<PostDocs, Long> {
	List<PostDocs> findByTitleContaining(String title);
}
