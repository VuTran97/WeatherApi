package com.skyapi.weatherforecast.service.impl;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.exception.LocationServiceException;
import com.skyapi.weatherforecast.repository.LocationRepository;
import com.skyapi.weatherforecast.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location add(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getByTrashed() {
        return locationRepository.findByTrashed();
    }

    @Override
    public Location getByCode(String code) {
        return locationRepository.getByCode(code);
    }

    @Override
    public Location update(Location requestBody) throws LocationServiceException {
        String code = requestBody.getCode();

        Location location = locationRepository.getByCode(code);
        if (location == null) {
            throw new LocationServiceException("Location not found");
        }

        location.setCityName(requestBody.getCityName());
        location.setCountryCode(requestBody.getCountryCode());
        location.setRegionName(requestBody.getRegionName());
        location.setCountryName(requestBody.getCountryName());
        location.setEnabled(requestBody.isEnabled());

        Location updatedLocation = locationRepository.save(location);
        return updatedLocation;
    }

    @Override
    public void delete(String code) throws LocationServiceException {

        if (!locationRepository.existsById(code)) {
            throw new LocationServiceException("Not found location");
        }

        locationRepository.trashByCode(code);
    }
}
