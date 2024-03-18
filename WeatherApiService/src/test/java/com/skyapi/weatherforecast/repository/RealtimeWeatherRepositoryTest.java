package com.skyapi.weatherforecast.repository;

import com.skyapi.weatherforecast.common.entity.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RealtimeWeatherRepositoryTest {

    @Autowired
    private RealtimeWeatherRepository repository;

    @Test
    public void updateRealtimeWeather() {
        String code = "DELHI_IN";
        Optional<RealtimeWeather> op = repository.findById(code);
        if (op.isPresent()) {
            RealtimeWeather realtimeWeather = op.get();
            realtimeWeather.setTemperature(12);
            realtimeWeather.setHumidity(60);
            realtimeWeather.setWindSpeed(10);
            realtimeWeather.setPrecipitation(70);
            realtimeWeather.setLastUpdated(new Date());
            realtimeWeather.setStatus("Sunny");
            RealtimeWeather updatedRW = repository.save(realtimeWeather);
            assertThat(updatedRW.getTemperature()).isEqualTo(12);
        }
    }

    @Test
    public void findByCountryCodeAndCityNotFound() {
        String countryCode = "JP";
        String cityName = "Tokyo";

        RealtimeWeather rw = repository.findByCountryCodeAndCity(countryCode, cityName);

        assertThat(rw).isNull();
    }

    @Test
    public void findByCountryCodeAndCityFound() {
        String countryCode = "IN";
        String cityName = "New Delhi";

        RealtimeWeather rw = repository.findByCountryCodeAndCity(countryCode, cityName);

        assertThat(rw).isNotNull();
    }
}
