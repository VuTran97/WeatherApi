package com.skyapi.weatherforecast.service.impl;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.common.entity.RealtimeWeather;
import com.skyapi.weatherforecast.exception.LocationServiceException;
import com.skyapi.weatherforecast.repository.RealtimeWeatherRepository;
import com.skyapi.weatherforecast.service.RealtimeWeatherService;
import org.springframework.stereotype.Service;

@Service
public class RealtimeWeatherServiceImpl implements RealtimeWeatherService {

    private RealtimeWeatherRepository realtimeWeatherRepository;

    public RealtimeWeatherServiceImpl(RealtimeWeatherRepository realtimeWeatherRepository) {
        this.realtimeWeatherRepository = realtimeWeatherRepository;
    }

    @Override
    public RealtimeWeather getByLocation(Location location) throws LocationServiceException {
        String countryCode = location.getCountryCode();
        String cityName = location.getCityName();

        RealtimeWeather realtimeWeather = realtimeWeatherRepository.findByCountryCodeAndCity(countryCode, cityName);

        if (realtimeWeather == null) {
            throw new LocationServiceException("No location found with the given country code and city name");
        }
        return realtimeWeather;
    }
}
