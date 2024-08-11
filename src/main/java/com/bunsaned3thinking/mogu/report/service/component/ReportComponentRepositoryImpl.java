package com.bunsaned3thinking.mogu.report.service.component;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.post.repository.module.jpa.PostJpaRepository;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.report.entity.ReportId;
import com.bunsaned3thinking.mogu.report.repository.ReportRepository;
import com.bunsaned3thinking.mogu.user.entity.User;
import com.bunsaned3thinking.mogu.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReportComponentRepositoryImpl implements ReportComponentRepository {
	private final PostJpaRepository postRepository;
	private final UserRepository userRepository;
	private final ReportRepository reportRepository;

	@Override
	public Optional<Post> findPostById(Long postId) {
		return postRepository.findById(postId);
	}

	@Override
	public Optional<User> findUserByUserId(String userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public boolean isReportExists(Long postId, Long userUid) {
		return reportRepository.existsById(ReportId.of(postId, userUid));
	}

	@Override
	public Report saveReport(Report report) {
		return reportRepository.save(report);
	}
}
