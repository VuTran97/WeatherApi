package com.skyapi.weatherforecast.service;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.common.entity.RealtimeWeather;
import com.skyapi.weatherforecast.exception.LocationServiceException;

public interface RealtimeWeatherService {

    RealtimeWeather getByLocation(Location location) throws LocationServiceException;
}
