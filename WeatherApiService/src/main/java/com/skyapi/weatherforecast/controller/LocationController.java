package com.skyapi.weatherforecast.controller;

import com.skyapi.weatherforecast.common.entity.Location;
import com.skyapi.weatherforecast.exception.LocationServiceException;
import com.skyapi.weatherforecast.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody @Valid Location location) {
        Location addedLocation = locationService.add(location);
        URI uri = URI.create("/api/v1/locations/" + addedLocation.getCode());
        return ResponseEntity.created(uri).body(addedLocation);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getByTrashed() {
        List<Location> locations = locationService.getByTrashed();
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Location> getByTrashed(@PathVariable("code") String code) {
        Location location = locationService.getByCode(code);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(location);
    }

    @PutMapping
    public ResponseEntity<Location> update(@RequestBody @Valid Location location) {
        try{
            Location updatedLocation = locationService.update(location);
            return ResponseEntity.ok(updatedLocation);
        }catch (LocationServiceException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Location> delete(@PathVariable("code") String code) {
        try{
            locationService.delete(code);
            return ResponseEntity.noContent().build();
        }catch (LocationServiceException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
