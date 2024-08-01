package com.bunsaned3thinking.mogu.ask.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.ask.entity.Ask;
import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface AskComponentRepository {
	Optional<User> findUserByUserId(String userId);

	Optional<Post> findPostById(Long postId);

	boolean existsAskByUserUidAndPostId(Long uid, Long postId);

	Ask saveAsk(Ask ask);

	List<Ask> findUserByUserUid(Long uid);

	List<Ask> findAskByPostId(Long postId);

	Optional<Ask> findAskByUserUidAndPostId(Long uid, Long postId);
}
