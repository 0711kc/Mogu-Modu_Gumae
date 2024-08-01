package com.bunsaned3thinking.mogu.location.service;

import org.springframework.stereotype.Service;

import com.bunsaned3thinking.mogu.location.entity.Location;
import com.bunsaned3thinking.mogu.location.repository.LocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
	private final LocationRepository locationRepository;

	@Override
	public Location createLocation(Double longitude, Double latitude) {
		if (longitude == null || latitude == null) {
			return null;
		}
		Location location = Location.of(longitude, latitude);
		return locationRepository.save(location);
	}
}
