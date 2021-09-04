package com.search.repository;

import com.search.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("SELECT H FROM House H WHERE H.price >= :minPrice AND H.price <= :maxPrice AND H.status = true")
    List<House> findByPriceRange(double minPrice, double maxPrice);

    List<House> findByLocationAndStatusTrue(String location);

    @Query("SELECT H FROM House H WHERE H.price >= :minPrice AND H.price <= :maxPrice AND H.location = :location AND H.status = true")
    List<House> findByPriceRangeAndLocation(double minPrice, double maxPrice, String location);

    @Query("SELECT H FROM House H WHERE H.latitude >= :latitude AND H.longitude <= :longitude AND H.status = true")
    List<House> findByGeolocation(double latitude, double longitude);

    List<House> findHousesByStatusTrueOrderByIdDesc();
}
