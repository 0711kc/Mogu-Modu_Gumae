package com.busaned_thinking.mogu.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.search.entity.Search;

public interface SearchRepository extends JpaRepository<Search, Long> {
}
