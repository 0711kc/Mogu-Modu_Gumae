package com.busaned_thinking.mogu.ask.repository.component;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.busaned_thinking.mogu.ask.entity.Ask;
import com.busaned_thinking.mogu.ask.repository.module.AskRepository;
import com.busaned_thinking.mogu.post.entity.Post;
import com.busaned_thinking.mogu.post.repository.module.PostRepository;
import com.busaned_thinking.mogu.user.entity.User;
import com.busaned_thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AskComponentRepositoryImpl implements AskComponentRepository {
	private final AskRepository askRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public Optional<Post> findPostById(Long postId) {
		return postRepository.findById(postId);
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
