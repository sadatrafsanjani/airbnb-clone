package com.rafsan.rentservice.service;

import com.rafsan.rentservice.dto.request.BookingRequest;
import com.rafsan.rentservice.dto.response.BookingResponse;
import com.rafsan.rentservice.model.Booking;
import com.rafsan.rentservice.repository.BookingRepository;
import com.rafsan.rentservice.repository.HouseRepository;
import com.rafsan.rentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private HouseRepository houseRepository;
    private EmailService emailService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              HouseRepository houseRepository,
                              EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.emailService = emailService;
    }

    @Override
    public List<BookingResponse> findApprovedBookings(){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByStatusTrueAndRejectionFalseOrderByIdDesc()){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    @Override
    public List<BookingResponse> findPendingBookings(){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByStatusFalseAndRejectionFalseOrderByIdDesc()){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    @Override
    public List<BookingResponse> findHouseBookingsById(long id){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByHouseIdOrderByIdDesc(id)){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    @Override
    public List<BookingResponse> findBookingsByCustomerId(long id){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByCustomerIdOrderByIdDesc(id)){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    @Override
    public BookingResponse saveBooking(BookingRequest request){

        if(bookingRepository.checkValidBookings(request.getCheckIn(), request.getCheckOut()).isEmpty()){

            return modelToDto(bookingRepository.save(dtoToModel(request)));
        }

        return null;
    }

    @Override
    public BookingResponse approveBooking(long id){

        bookingRepository.approveBooking(id);
        Booking booking = bookingRepository.getById(id);

        if(booking.isStatus()){

            emailService.notifyBookingApproval(booking.getCustomer().getEmail());

            return modelToDto(booking);
        }

        return null;
    }

    @Override
    public BookingResponse rejectBooking(long id){

        bookingRepository.rejectBooking(id);
        Booking booking = bookingRepository.getById(id);

        if(!booking.isRejection()){

            emailService.notifyBookingRejection(booking.getCustomer().getEmail());

            return modelToDto(booking);
        }

        return null;
    }

    @Override
    public BookingResponse cancelBooking(long id){

        bookingRepository.cancelBooking(id);
        Booking booking = bookingRepository.getById(id);

        if(booking.isCancel()){

            emailService.notifyBookingCancel(booking.getCustomer().getEmail());

            return modelToDto(booking);
        }

        return null;
    }

    private Booking dtoToModel(BookingRequest request){

        return Booking
                .builder()
                .house(houseRepository.getById(request.getHouseId()))
                .customer(userRepository.getById(request.getCustomerId()))
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .status(false)
                .rejection(false)
                .cancel(false)
                .build();
    }

    private BookingResponse modelToDto(Booking booking){

        return BookingResponse
                .builder()
                .id(booking.getId())
                .customerId(booking.getCustomer().getId())
                .houseId(booking.getHouse().getId())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .status(booking.isStatus())
                .rejection(booking.isRejection())
                .cancel(booking.isCancel())
                .build();
    }
}
