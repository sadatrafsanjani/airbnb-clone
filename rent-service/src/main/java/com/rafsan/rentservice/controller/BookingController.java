package com.rafsan.rentservice.controller;

import com.rafsan.rentservice.dto.request.BookingRequest;
import com.rafsan.rentservice.dto.response.BookingResponse;
import com.rafsan.rentservice.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rental/bookings")
@Slf4j
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    ResponseEntity<BookingResponse> saveBooking(@RequestBody BookingRequest request){

        log.info("Inside saveBooking()");

        BookingResponse response = bookingService.saveBooking(request);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findApprovedBookings")
    ResponseEntity<List<BookingResponse>> findApprovedBookings(){

        List<BookingResponse> responses = bookingService.findApprovedBookings();

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findPendingBookings")
    ResponseEntity<List<BookingResponse>> findPendingBookings(){

        List<BookingResponse> responses = bookingService.findPendingBookings();

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findHouseBookingsById/{id}")
    ResponseEntity<List<BookingResponse>> findHouseBookingsById(@PathVariable("id") long id){

        List<BookingResponse> responses = bookingService.findHouseBookingsById(id);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findBookingsByCustomerId/{id}")
    ResponseEntity<List<BookingResponse>> findBookingsByCustomerId(@PathVariable("id") long id){

        List<BookingResponse> responses = bookingService.findBookingsByCustomerId(id);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve/{id}")
    ResponseEntity<?> approveBookingPost(@PathVariable("id") long id){

        BookingResponse response = bookingService.approveBooking(id);

        if(response != null){

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    ResponseEntity<?> rejectBookingPost(@PathVariable("id") long id){

        BookingResponse response = bookingService.rejectBooking(id);

        if(response != null){

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel/{id}")
    ResponseEntity<?> cancelBookingPost(@PathVariable("id") long id){

        BookingResponse response = bookingService.cancelBooking(id);

        if(response != null){

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }
}
