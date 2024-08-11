package com.bunsaned3thinking.mogu.report.service.component;

import java.util.Optional;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface ReportComponentRepository {
	Optional<Post> findPostById(Long postId);

	Optional<User> findUserByUserId(String userId);

	boolean isReportExists(Long postId, Long userUid);

	Report saveReport(Report report);
}
