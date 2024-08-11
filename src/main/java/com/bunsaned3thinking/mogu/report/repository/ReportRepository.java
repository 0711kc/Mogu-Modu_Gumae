package com.bunsaned3thinking.mogu.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.report.entity.Report;
import com.bunsaned3thinking.mogu.report.entity.ReportId;

public interface ReportRepository extends JpaRepository<Report, ReportId> {
}
