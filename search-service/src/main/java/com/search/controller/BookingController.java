package com.search.controller;

import com.search.dto.request.BookingRequest;
import com.search.dto.response.BookingResponse;
import com.search.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/search/bookings")
@Slf4j
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/test")
    public String test(){

        return "Search Service Testing";
    }

    @PostMapping
    ResponseEntity<BookingResponse> saveBooking(@RequestBody BookingRequest request) {

        log.info("Inside saveBooking()");

        CompletableFuture<BookingResponse>  response = bookingService.bookHouse(request);

        if(response.isDone()){

            try {

                BookingResponse bookingResponse = response.get();

                return ResponseEntity.ok(bookingResponse);
            } catch (InterruptedException | ExecutionException e) {
                log.error("Inside saveBooking()" + e.getMessage());
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok().build();
    }
}
