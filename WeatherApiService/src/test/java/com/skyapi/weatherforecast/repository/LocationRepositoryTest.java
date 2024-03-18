package com.skyapi.weatherforecast.repository;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.common.entity.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testAddSuccess() {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Location savedLocation = locationRepository.save(location);

        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getCode()).isEqualTo("NYC_USA");
    }

    @Test
    public void testFindByTrashed() {
        List<Location> locations = locationRepository.findByTrashed();
        assertThat(locations).isNotEmpty();
        locations.forEach(System.out::println);
    }

    @Test
    public void testGetByCode() {
        String code = "DELHI_IN";
        Location location = locationRepository.getByCode(code);
        assertThat(location).isNotNull();
    }

    @Test
    public void testTrashByCode() {
        String code = "DELHI_IN";

        locationRepository.trashByCode(code);

        Location location = locationRepository.getByCode(code);

        assertThat(location).isNull();
    }

    @Test
    public void testUpdateRealtimeWeather() {
        String code = "DELHI_IN";
        Location location = locationRepository.getByCode(code);

        RealtimeWeather realtimeWeather = location.getRealtimeWeather();

        if (realtimeWeather == null) {
            realtimeWeather = new RealtimeWeather();
            realtimeWeather.setLocation(location);
            location.setRealtimeWeather(realtimeWeather);
        }

        realtimeWeather.setTemperature(10);
        realtimeWeather.setHumidity(60);
        realtimeWeather.setWindSpeed(10);
        realtimeWeather.setPrecipitation(70);
        realtimeWeather.setLastUpdated(new Date());
        realtimeWeather.setStatus("Sunny");

        Location updatedLocation = locationRepository.save(location);

        assertThat(updatedLocation.getRealtimeWeather().getLocationCode()).isEqualTo(code);
    }
}
