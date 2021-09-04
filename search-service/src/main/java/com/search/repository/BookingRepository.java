package com.search.repository;

import com.search.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByHouseIdAndRejectionFalseAndCancelFalse(long id);
}
