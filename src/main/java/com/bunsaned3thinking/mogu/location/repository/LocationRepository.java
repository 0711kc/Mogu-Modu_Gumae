package com.bunsaned3thinking.mogu.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunsaned3thinking.mogu.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
