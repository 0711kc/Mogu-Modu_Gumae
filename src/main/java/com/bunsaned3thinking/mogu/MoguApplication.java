package com.bunsaned3thinking.mogu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bunsaned3thinking.mogu.post.repository.module.elasticsearch.PostDocsElasticRepository;

@SpringBootApplication
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = PostDocsElasticRepository.class))
public class MoguApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoguApplication.class, args);
	}

}
