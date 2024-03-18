package com.skyapi.weatherforecast.repository;

import com.skyapi.weatherforecast.common.entity.Location;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, String> {

    @Query("SELECT l FROM Location l WHERE l.trashed = false")
    List<Location> findByTrashed();

    @Query("SELECT l FROM Location l WHERE l.trashed = false AND l.code = ?1")
    Location getByCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE Location l SET l.trashed = true WHERE l.code = ?1")
    void trashByCode(String code);
}
