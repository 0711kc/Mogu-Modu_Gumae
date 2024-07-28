package com.busaned_thinking.mogu.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busaned_thinking.mogu.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
