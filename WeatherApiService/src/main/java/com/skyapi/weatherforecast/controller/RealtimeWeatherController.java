package com.skyapi.weatherforecast.controller;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.common.entity.RealtimeWeather;
import com.skyapi.weatherforecast.exception.GeoLocationServiceException;
import com.skyapi.weatherforecast.exception.LocationServiceException;
import com.skyapi.weatherforecast.service.GeoLocationService;
import com.skyapi.weatherforecast.service.RealtimeWeatherService;
import com.skyapi.weatherforecast.util.CommonUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/realtime")
public class RealtimeWeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeWeatherController.class);

    private GeoLocationService geoLocationService;

    private RealtimeWeatherService realtimeWeatherService;

    public RealtimeWeatherController(GeoLocationService geoLocationService, RealtimeWeatherService realtimeWeatherService) {
        this.geoLocationService = geoLocationService;
        this.realtimeWeatherService = realtimeWeatherService;
    }

    @GetMapping
    public ResponseEntity<RealtimeWeather> getRealtimeWeatherByIPAddress(HttpServletRequest request) {
        String ipAddress = CommonUtility.getIPAddress(request);
        try {
            Location location = geoLocationService.getLocation(ipAddress);
            RealtimeWeather realtimeWeather = realtimeWeatherService.getByLocation(location);
            return ResponseEntity.ok(realtimeWeather);
        } catch (GeoLocationServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        } catch (LocationServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }
}
