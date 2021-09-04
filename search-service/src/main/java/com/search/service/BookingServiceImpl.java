package com.search.service;

import com.search.dto.request.BookingRequest;
import com.search.dto.response.BookingResponse;
import com.search.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private RestTemplate restTemplate;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @Async
    public CompletableFuture<BookingResponse> bookHouse(BookingRequest request){

        String url = String.format("http://RENTAL-SERVICE/api/bookings/", request);
        BookingResponse response = restTemplate.postForObject(url, request, BookingResponse.class);

        return CompletableFuture.completedFuture(response);
    }

}
