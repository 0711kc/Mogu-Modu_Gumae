package com.bunsaned3thinking.mogu.report.service.module;

import org.springframework.http.ResponseEntity;

import com.bunsaned3thinking.mogu.report.dto.request.ReportRequest;
import com.bunsaned3thinking.mogu.report.dto.response.ReportResponse;

public interface ReportService {
	ResponseEntity<ReportResponse> createReport(Long postId, String userId, ReportRequest reportRequest);
}
