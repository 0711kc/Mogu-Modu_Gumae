package com.busaned_thinking.mogu.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
