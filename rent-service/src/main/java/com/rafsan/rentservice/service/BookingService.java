package com.rafsan.rentservice.service;

import com.rafsan.rentservice.dto.request.BookingRequest;
import com.rafsan.rentservice.dto.request.BookingUpdateRequest;
import com.rafsan.rentservice.dto.response.BookingResponse;
import java.util.List;

public interface BookingService {

    List<BookingResponse> findApprovedBookings();
    List<BookingResponse> findPendingBookings();
    List<BookingResponse> findHouseBookingsById(long id);
    List<BookingResponse> findBookingsByCustomerId(long id);
    BookingResponse saveBooking(BookingRequest request);
    BookingResponse approveBooking(BookingUpdateRequest request);
    BookingResponse rejectBooking(BookingUpdateRequest request);
    BookingResponse cancelBooking(BookingUpdateRequest request);
}
