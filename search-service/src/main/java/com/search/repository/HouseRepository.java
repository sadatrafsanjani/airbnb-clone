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

    @Query("SELECT H FROM House H WHERE H.location LIKE %:location% AND H.status = true")
    List<House> searchByLocation(String location);

    @Query("SELECT H FROM House H WHERE H.price >= :minPrice AND H.price <= :maxPrice AND H.location LIKE %:location% AND H.status = true")
    List<House> findByPriceRangeAndLocation(double minPrice, double maxPrice, String location);

    List<House> findHousesByStatusTrueOrderByIdDesc();
}
