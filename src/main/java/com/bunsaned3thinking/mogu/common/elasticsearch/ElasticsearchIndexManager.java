package com.bunsaned3thinking.mogu.common.elasticsearch;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Component;

import com.bunsaned3thinking.mogu.post.entity.PostDoc;

import io.micrometer.common.lang.NonNullApi;

@Component
@NonNullApi
public class ElasticsearchIndexManager implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		IndexOperations indexOperations = elasticsearchOperations.indexOps(PostDoc.class);

		// 인덱스 삭제
		if (indexOperations.exists()) {
			indexOperations.delete();
		}

		// 인덱스 생성
		indexOperations.create();
		indexOperations.putMapping(indexOperations.createMapping());
	}

	@Override
	public void destroy() {
		IndexOperations indexOperations = elasticsearchOperations.indexOps(PostDoc.class);
		if (indexOperations.exists()) {
			indexOperations.delete();
		}
	}
}
