package com.bunsaned3thinking.mogu.searchhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.searchhistory.entity.SearchHistory;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}
