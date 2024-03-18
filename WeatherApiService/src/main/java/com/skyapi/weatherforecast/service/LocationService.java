package com.skyapi.weatherforecast.service;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.exception.LocationServiceException;

import java.util.List;

public interface LocationService {

    Location add(Location location);

    List<Location> getByTrashed();

    Location getByCode(String code);

    Location update(Location requestBody) throws LocationServiceException;

    void delete(String code) throws LocationServiceException;
}
