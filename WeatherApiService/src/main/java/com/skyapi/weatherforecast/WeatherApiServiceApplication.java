package com.skyapi.weatherforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherApiServiceApplication {

    public static void main(String[] args) {
        System.out.println("test fork");
        SpringApplication.run(WeatherApiServiceApplication.class, args);
    }

}
