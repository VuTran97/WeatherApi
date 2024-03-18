package com.skyapi.weatherforecast.service.impl;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.exception.GeoLocationServiceException;
import com.skyapi.weatherforecast.service.GeoLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    private String DBPath = "ip2locationdb/IP2LOCATION-LITE-DB3.BIN";

    private IP2Location ip2Location = new IP2Location();

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoLocationServiceImpl.class);

    public GeoLocationServiceImpl() {
        try{
            // Load the resource from the classpath
            Resource resource = new ClassPathResource(DBPath);
            // Get the file from the resource
            File dbFile = resource.getFile();
            // Get the absolute path of the file
            String DBPath = dbFile.getAbsolutePath();

            ip2Location.Open(DBPath);
        }catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public Location getLocation(String ipAddress) throws GeoLocationServiceException {
        try{
            IPResult ipResult = ip2Location.IPQuery(ipAddress);
            if (!"OK".equals(ipResult.getStatus())) {
                throw new GeoLocationServiceException("Geolocation failed with status: " +ipResult.getStatus());
            }
            return new Location(ipResult.getCity(), ipResult.getRegion(), ipResult.getCountryLong(), ipResult.getCountryShort());
        }catch (Exception ex) {
            throw new GeoLocationServiceException("Error querying IP database", ex);
        }
    }
}
