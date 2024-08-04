package com.bunsaned3thinking.mogu.post.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostDocs;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.repository.module.PostDetailRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostImageRepository;
import com.bunsaned3thinking.mogu.post.repository.module.elasticsearch.PostDocsElasticRepository;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostComponentRepositoryImpl implements PostComponentRepository {
	private final PostDocsElasticRepository postDocsElasticRepository;
	private final UserRepository userRepository;
	private final PostDetailRepository postDetailRepository;
	private final PostImageRepository postImageRepository;
	private final PostJpaRepository postJpaRepository;

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public void saveAllPostImages(List<PostImage> postImages) {
		postImageRepository.saveAll(postImages);
	}

	@Override
	public void savePostDetail(PostDetail postDetail) {
		postDetailRepository.save(postDetail);
	}

	@Override
	public Post savePost(Post post) {
		Post savedPost = postJpaRepository.save(post);
		postDocsElasticRepository.save(
			PostDocs.of(post.getId(), post.getTitle(), post.getPostDetail().getContent(),
				post.getUser().getNickname()));
		return savedPost;
	}

	@Override
	public Optional<Post> findPostById(Long id) {
		return postJpaRepository.findById(id);
	}

	@Override
	public void deletePostDetailByPostId(Long id) {
		postDetailRepository.deleteByPostId(id);
	}

	@Override
	public List<Post> searchPostsByTitle(String keyword) {
		List<PostDocs> postDocses = postDocsElasticRepository.findByTitleContaining(keyword);
		if (postDocses.isEmpty()) {
			return List.of();
		}
		for (PostDocs postDocs : postDocses) {
			System.out.println(postDocs.getTitle());
		}

		// String[] fields = {"title"};
		// Pageable aa = PageRequest.of(0, 10);
		// Page<PostDocs> postDocsPage = postDocsElasticRepository.searchSimilar(referencePost, fields, pageable);
		// Like like = Like.of(l -> l
		// 	.text(referencePost.getTitle())  // title 필드를 기반으로 유사 문서를 찾기 위해 추가
		// );
		// MoreLikeThisQuery moreLikeThisQuery = QueryBuilders.moreLikeThis()
		// 	.fields("title").like(like).minTermFreq(1).maxQueryTerms(12).build();
		// SearchRequest searchRequest = SearchRequest.of(s -> s
		// 	.index("posts")  // 검색할 인덱스 이름
		// 	.query(q -> q
		// 		.moreLikeThis(moreLikeThisQuery)
		// 	)
		// );
		// RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
		// RestClientTransport transport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());
		// ElasticsearchClient client = new ElasticsearchClient(transport);
		// SearchResponse<JsonData> searchResponse = null;
		// try {
		// 	searchResponse = client.search(searchRequest, JsonData.class);
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// }
		// // 5. 결과 처리
		// List<Hit<JsonData>> hits = searchResponse.hits().hits();
		// if (hits.isEmpty()) {
		// 	System.out.println("hits isEmpty");
		// } else {
		// 	for (Hit<JsonData> hit : hits) {
		// 		System.out.println(hit.source());  // 문서의 소스 데이터를 출력
		// 		System.out.println(hit.index());
		// 		System.out.println(hit.id());
		// 		System.out.println(hit.fields());
		// 	}
		// }

		return postDocses.stream()
			.map(postDocs -> postJpaRepository.findById(postDocs.getId()))
			.filter(Optional::isPresent)
			.map(Optional::get)
			.toList();
	}

}
