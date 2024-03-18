package com.skyapi.weatherforecast.exception;

public class GeoLocationServiceException extends Exception{

    public GeoLocationServiceException(String message, Throwable cause) {
        super(message,cause);
    }

    public GeoLocationServiceException(String message) {
        super(message);
    }
}
