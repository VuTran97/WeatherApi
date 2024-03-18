package com.skyapi.weatherforecast.service;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.exception.GeoLocationServiceException;

public interface GeoLocationService {

    Location getLocation(String ipAddress) throws GeoLocationServiceException;
}
