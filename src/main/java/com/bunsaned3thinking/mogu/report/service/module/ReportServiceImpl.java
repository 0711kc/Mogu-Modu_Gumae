package com.bunsaned3thinking.mogu.report.service.module;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.post.entity.Post;
import com.bunsaned3thinking.mogu.report.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.report.dto.response.ReportResponse;
import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.report.service.component.ReportComponentRepository;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
	private final ReportComponentRepository reportComponentRepository;

	@Override
	public ResponseEntity<ReportResponse> createReport(Long postId, String userId, ReportRequest reportRequest) {
		Post post = reportComponentRepository.findPostById(postId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 게시물을 찾을 수 없습니다."));
		User user = reportComponentRepository.findUserByUserId(userId)
			.orElseThrow(() -> new EntityNotFoundException("[Error] 사용자를 찾을 수 없습니다."));
		if (post.getUser().equals(user)) {
			throw new IllegalArgumentException("[Error] 자신의 게시글은 신고할 수 없습니다.");
		}
		boolean isReportExists = reportComponentRepository.isReportExists(post.getId(), user.getUid());
		if (isReportExists) {
			throw new IllegalArgumentException("[Error] 게시물 신고는 한 번만 가능합니다.");
		}
		Report report = reportRequest.toEntity(post, user);
		Report savedReport = reportComponentRepository.saveReport(report);
		post.getReports().add(savedReport);
		user.getReports().add(savedReport);
		return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(ReportResponse.from(savedReport));
	}
}
