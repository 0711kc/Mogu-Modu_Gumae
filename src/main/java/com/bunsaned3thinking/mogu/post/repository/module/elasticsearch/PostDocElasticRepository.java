package com.bunsaned3thinking.mogu.post.repository.module.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bunsaned3thinking.mogu.post.entity.PostDoc;

public interface PostDocElasticRepository extends ElasticsearchRepository<PostDoc, Long> {
	@Query(value = """
		{
			"bool": {
				"must": [
					{ "range": { "id": { "lt": "?0" } } },
					{
						"bool": {
							"should": [
								{ "wildcard": { "title": "*?1*" } },
								{ "wildcard": { "content": "*?1*" } }
							]
						}
					}
				]
			}
		}
		""")
	List<PostDoc> searchPostDocBy(Long cursor, String keyword);

	@Query(value = """
		{
			"bool": {
				"should": [
					{ "wildcard": { "title": "*?0*" } },
					{ "wildcard": { "content": "*?0*" } }
				]
			}
		}
		""")
	List<PostDoc> searchPostDocBy(String keyword);
}
