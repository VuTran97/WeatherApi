package com.skyapi.weatherforecast.repository;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class IP2LocationTest {

    private String DBPath = "ip2location/IP2LOCATION-LITE-DB3.BIN";

    @Test
    public void testInvalidIP() throws IOException {
        IP2Location ip2Location = new IP2Location();
        ip2Location.Open(DBPath);

        String ipAddress  = "ABC";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);

        assertThat(ipResult.getStatus()).isEqualTo("INVALID_IP_ADDRESS");

        System.out.println("ipResult: "+ipResult);
    }

    @Test
    public void testValidIP() throws IOException {
        IP2Location ip2Location = new IP2Location();
        ip2Location.Open(DBPath);

        String ipAddress  = "108.30.178.78";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);

        assertThat(ipResult.getStatus()).isEqualTo("OK");

        System.out.println("ipResult: "+ipResult);
    }
}
