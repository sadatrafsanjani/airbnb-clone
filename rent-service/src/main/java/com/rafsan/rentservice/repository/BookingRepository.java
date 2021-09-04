package com.rafsan.rentservice.repository;

import com.rafsan.rentservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByStatusTrueAndRejectionFalseOrderByIdDesc();

    List<Booking> findAllByStatusFalseAndRejectionFalseOrderByIdDesc();

    List<Booking> findAllByHouseIdOrderByIdDesc(long id);

    List<Booking> findAllByCustomerIdOrderByIdDesc(long id);

    @Query("SELECT B FROM Booking B WHERE B.checkIn <= :checkOut AND B.checkOut >= :checkIn AND B.cancel = false AND B.rejection = false")
    List<Booking> checkValidBookings(Date checkIn, Date checkOut);

    @Modifying
    @Query("UPDATE Booking B SET B.status = true WHERE B.id = :id")
    void approveBooking(long id);

    @Modifying
    @Query("UPDATE Booking B SET B.rejection = true WHERE B.id = :id")
    void rejectBooking(long id);

    @Modifying
    @Query("UPDATE Booking B SET B.cancel = true WHERE B.id = :id")
    void cancelBooking(long id);
}
