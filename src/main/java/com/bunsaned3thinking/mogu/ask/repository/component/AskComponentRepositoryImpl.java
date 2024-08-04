package com.bunsaned3thinking.mogu.ask.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.ask.repository.module.AskRepository;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.repository.module.elasticsearch.PostDocsElasticRepository;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AskComponentRepositoryImpl implements AskComponentRepository {
	private final AskRepository askRepository;
	private final UserRepository userRepository;
	private final PostDocsElasticRepository postDocsElasticRepository;
	private final PostJpaRepository postJpaRepository;

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public Optional<Post> findPostById(Long postId) {
		// return postDocsElasticRepository.findById(postId);
		return postJpaRepository.findById(postId);
	}

	@Override
	public boolean existsAskByUserUidAndPostId(Long uid, Long postId) {
		return askRepository.existsByUserUidAndPostId(uid, postId);
	}

	@Override
	public Ask saveAsk(Ask ask) {
		return askRepository.save(ask);
	}

	@Override
	public List<Ask> findUserByUserUid(Long uid) {
		return askRepository.findByUserUid(uid);
	}

	@Override
	public List<Ask> findAskByPostId(Long postId) {
		return askRepository.findByPostId(postId);
	}

	@Override
	public Optional<Ask> findAskByUserUidAndPostId(Long uid, Long postId) {
		return askRepository.findByUserUidAndPostId(uid, postId);
	}
}
