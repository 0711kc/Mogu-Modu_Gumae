package com.bunsaned3thinking.mogu.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
