package com.skyapi.weatherforecast.repository;

import com.skyapi.weatherforecast.common.entity.RealtimeWeather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RealtimeWeatherRepository extends CrudRepository<RealtimeWeather, String> {

    @Query("SELECT rw FROM RealtimeWeather rw WHERE rw.location.countryCode = ?1 AND rw.location.cityName = ?2")
    RealtimeWeather findByCountryCodeAndCity(String countryCode, String city);
}
