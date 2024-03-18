package com.skyapi.weatherforecast.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.exception.LocationServiceException;
import com.skyapi.weatherforecast.service.GeoLocationService;
import com.skyapi.weatherforecast.service.LocationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    private static final String END_POINT = "/api/v1/locations";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LocationService locationService;


    @Test
    public void testAddShouldReturn400BadRequest() throws Exception {
        Location location = new Location();
        String bodyContent = objectMapper.writeValueAsString(location);
        mockMvc.perform(post(END_POINT).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testAddShouldReturn201Created() throws Exception {
        Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(locationService.add(location)).thenReturn(location);

        String bodyContent = objectMapper.writeValueAsString(location);
        mockMvc.perform(post(END_POINT).contentType("application/json").content(bodyContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code", is("NYC_USA")))
                .andDo(print());
    }

    @Test
    public void testValidateRequestLocationCode() throws Exception {
        Location location = new Location();
        //location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(locationService.add(location)).thenReturn(location);

        String bodyContent = objectMapper.writeValueAsString(location);
        mockMvc.perform(post(END_POINT).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void testGetByTrashedShouldReturn204NoContent() throws Exception {

        Mockito.when(locationService.getByTrashed()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(END_POINT).content("application/json")).andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testGetByTrashedShouldReturn200Ok() throws Exception {

        Location location1 = new Location();
        location1.setCode("NYC_USA");
        location1.setCityName("New York City");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountryName("United States of America");
        location1.setEnabled(true);

        Location location2 = new Location();
        location2.setCode("LACA_USA");
        location2.setCityName("Los Angeles");
        location2.setRegionName("California");
        location2.setCountryCode("US");
        location2.setCountryName("United States of America");
        location2.setEnabled(true);

        Mockito.when(locationService.getByTrashed()).thenReturn(List.of(location1, location2));

        mockMvc.perform(get(END_POINT).content("application/json")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].code", is("NYC_USA")))
                .andDo(print());
    }

    @Test
    public void testGetByCodeShouldReturn404NotFound() throws Exception {
        String code = "ABC";

        String path = END_POINT + "/" + code;
        mockMvc.perform(get(path)).andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testGetByCodeShouldReturn200Ok() throws Exception {
        String code = "LACA_USA";

        Location location = new Location();
        location.setCode("LACA_USA");
        location.setCityName("Los Angeles");
        location.setRegionName("California");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);

        Mockito.when(locationService.getByCode(code)).thenReturn(location);

        String path = END_POINT + "/" + code;
        mockMvc.perform(get(path)).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("LACA_USA")))
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn404NotFound() throws Exception {
        Location requestBody = new Location();
        requestBody.setCode("ABC");
        requestBody.setCountryName("ABC");
        requestBody.setCityName("ABC");
        requestBody.setCountryCode("US");
        requestBody.setRegionName("abc");
        requestBody.setEnabled(true);

        Mockito.when(locationService.update(requestBody)).thenThrow(new LocationServiceException("Not found location"));

        String bodyContent = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(put(END_POINT).contentType("application/json").content(bodyContent))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        Location requestBody = new Location();
        requestBody.setCode("ABC");
        requestBody.setCountryName("ABC");
        requestBody.setCityName("ABC");
        requestBody.setRegionName("abc");
        requestBody.setEnabled(true);

        Mockito.when(locationService.update(requestBody)).thenReturn(requestBody);

        String bodyContent = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(put(END_POINT).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testUpdateShouldReturn200Ok() throws Exception {
        Location requestBody = new Location();
        requestBody.setCode("ABC");
        requestBody.setCountryName("ABC");
        requestBody.setCityName("ABC");
        requestBody.setCountryCode("US");
        requestBody.setRegionName("abc");
        requestBody.setEnabled(true);

        Mockito.when(locationService.update(requestBody)).thenReturn(requestBody);

        String bodyContent = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(put(END_POINT).contentType("application/json").content(bodyContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("ABC")))
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        String code = "ABC";

        Mockito.doThrow(new LocationServiceException("Not found location")).when(locationService).delete(code);

        String requestUrl = END_POINT + "/" + code;

        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeleteShouldReturn204NoContent() throws Exception {
        String code = "ABC";

        Mockito.doNothing().when(locationService).delete(code);

        String requestUrl = END_POINT + "/" + code;

        mockMvc.perform(delete(requestUrl))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
