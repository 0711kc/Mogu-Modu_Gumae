package com.bunsaned3thinking.mogu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.bunsaned3thinking.mogu.post.repository.module.elasticsearch.PostDocsElasticRepository;

@EnableElasticsearchRepositories(basePackageClasses = PostDocsElasticRepository.class)
@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {
	@Value("${elasticsearch.rest.uris}")
	private String elasticUrl;

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
			.connectedTo(elasticUrl)
			.build();
	}
}
