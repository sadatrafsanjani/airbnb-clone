package com.search.repository;

import com.search.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT R FROM Review R WHERE R.booking.house.id = : id")
    List<Review> findByHouseId(long id);
}
