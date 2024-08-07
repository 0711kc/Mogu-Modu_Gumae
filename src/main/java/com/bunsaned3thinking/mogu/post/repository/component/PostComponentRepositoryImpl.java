package com.bunsaned3thinking.mogu.post.repository.component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bunsaned3thinking.mogu.post.entity.HiddenPost;
import com.bunsaned3thinking.mogu.post.entity.HiddenPostId;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.entity.PostDetail;
import com.bunsaned3thinking.mogu.post.entity.PostDocs;
import com.bunsaned3thinking.mogu.post.entity.PostImage;
import com.bunsaned3thinking.mogu.post.repository.module.HiddenPostRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostDetailRepository;
import com.bunsaned3thinking.mogu.post.repository.module.PostImageRepository;
import com.bunsaned3thinking.mogu.post.repository.module.elasticsearch.PostDocsElasticRepository;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.report.repository.ReportRepository;
import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;
import com.bunsaned3thinking.mogu.searchhistory.repository.SearchHistoryRepository;
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
	private final ReportRepository reportRepository;
	private final SearchHistoryRepository searchHistoryRepository;
	private final HiddenPostRepository hiddenPostRepository;

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
	public Report saveReport(Report report) {
		return reportRepository.save(report);
	}

	@Override
	public SearchHistory saveSearchHistory(String keyword, User user) {
		return searchHistoryRepository.save(SearchHistory.of(keyword, user));
	}

	@Override
	public Optional<Post> findPostById(Long id) {
		return postJpaRepository.findById(id);
	}

	@Override
	public Optional<SearchHistory> findSearchHistoryById(Long searchHistoryId) {
		return searchHistoryRepository.findById(searchHistoryId);
	}

	@Override
	public void deletePostDetailByPostId(Long id) {
		postDetailRepository.deleteByPostId(id);
	}

	@Override
	public void deleteSearchHistoryById(Long searchHistoryId) {
		searchHistoryRepository.deleteById(searchHistoryId);
	}

	@Override
	public List<Post> findAllPostsByPurchaseDate(LocalDate purchaseDate) {
		return postJpaRepository.findByPurchaseDate(purchaseDate);
	}

	@Override
	public void saveAllPosts(List<Post> posts) {
		postJpaRepository.saveAll(posts);
	}

	@Override
	public Slice<Post> findNextPagePosts(Long userUid, Long cursor, PageRequest pageRequest) {
		return postJpaRepository.findNextPage(userUid, cursor, pageRequest);
	}

	@Override
	public void saveHiddenPost(HiddenPost hiddenPost) {
		hiddenPostRepository.save(hiddenPost);
	}

	@Override
	public void deleteHiddenPostById(HiddenPostId hiddenPostId) {
		hiddenPostRepository.deleteById(hiddenPostId);
	}

	@Override
	public boolean existsHiddenPostById(HiddenPostId hiddenPostId) {
		return hiddenPostRepository.existsById(hiddenPostId);
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

	@Override
	public List<Report> findAllReport() {
		return reportRepository.findAll();
	}

	@Override
	public boolean isReportExists(Post post, User user) {
		return findAllReport().stream()
			.anyMatch(report -> report.getPost().equals(post) && report.getUser().equals(user));
	}

	@Override
	public List<Post> findAllReportedPost() {
		return postJpaRepository.findAll().stream()
			.filter(post -> !post.getReports().isEmpty())
			.collect(Collectors.toList());
	}
}
