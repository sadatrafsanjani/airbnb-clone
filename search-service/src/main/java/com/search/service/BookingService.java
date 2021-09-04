package com.search.service;

import com.search.dto.request.BookingRequest;
import com.search.dto.response.BookingResponse;

import java.util.concurrent.CompletableFuture;

public interface BookingService {

    CompletableFuture<BookingResponse> bookHouse(BookingRequest request);
}
