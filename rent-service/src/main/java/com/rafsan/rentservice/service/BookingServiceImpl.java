package com.rafsan.rentservice.service;

import com.rafsan.rentservice.dto.request.BookingRequest;
import com.rafsan.rentservice.dto.request.BookingUpdateRequest;
import com.rafsan.rentservice.dto.response.BookingResponse;
import com.rafsan.rentservice.model.Booking;
import com.rafsan.rentservice.repository.BookingRepository;
import com.rafsan.rentservice.repository.HouseRepository;
import com.rafsan.rentservice.repository.RoleRepository;
import com.rafsan.rentservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private HouseRepository houseRepository;
    private EmailService emailService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository,
                              UserRepository userRepository,
                              RoleRepository roleRepository,
                              HouseRepository houseRepository,
                              EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.houseRepository = houseRepository;
        this.emailService = emailService;
    }

    /* Find list of approved bookings */
    @Override
    public List<BookingResponse> findApprovedBookings(){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByStatusTrueAndRejectionFalseOrderByIdDesc()){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    /* Find list of pending bookings */
    @Override
    public List<BookingResponse> findPendingBookings(){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByStatusFalseAndRejectionFalseOrderByIdDesc()){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    /* Find list of house bookings by id */
    @Override
    public List<BookingResponse> findHouseBookingsById(long id){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByHouseIdOrderByIdDesc(id)){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    /* Find list of bookings of a particular customer */
    @Override
    public List<BookingResponse> findBookingsByCustomerId(long id){

        List<BookingResponse> responses = new ArrayList<>();

        for(Booking booking : bookingRepository.findAllByCustomerIdOrderByIdDesc(id)){

            responses.add(modelToDto(booking));
        }

        return responses;
    }

    /* book a house */
    @Override
    public synchronized BookingResponse saveBooking(BookingRequest request){

        if(bookingRepository.checkValidBookings(request.getCheckIn(), request.getCheckOut()).isEmpty()){

            return modelToDto(bookingRepository.save(dtoToModel(request)));
        }

        return null;
    }

    /* Approve a house booking by Admin */
    @Override
    public BookingResponse approveBooking(BookingUpdateRequest request){

        if(userRepository.getById(request.getUserId()).getRoles().contains(roleRepository.findByRoleName("ROLE_ADMIN"))){

            bookingRepository.approveBooking(request.getBookingId());
            Booking booking = bookingRepository.getById(request.getBookingId());

            if(booking.isStatus()){

                emailService.notifyBookingApproval(booking.getCustomer().getEmail());

                return modelToDto(booking);
            }
        }

        return null;
    }

    /* Reject a house booking by Admin */
    @Override
    public BookingResponse rejectBooking(BookingUpdateRequest request){

        if(userRepository.getById(request.getUserId()).getRoles().contains(roleRepository.findByRoleName("ROLE_ADMIN"))){

            bookingRepository.rejectBooking(request.getBookingId());
            Booking booking = bookingRepository.getById(request.getBookingId());

            if(!booking.isRejection()){

                emailService.notifyBookingRejection(booking.getCustomer().getEmail());

                return modelToDto(booking);
            }
        }

        return null;
    }

    /* Cancel a house booking at least 7 days before check in by Customer */
    @Override
    public BookingResponse cancelBooking(BookingUpdateRequest request){

        Booking booking = bookingRepository.getById(request.getUserId());

        if(request.getUserId() == booking.getId()){

            Date currentDate = new Date(System.currentTimeMillis());
            Date checkInDate = booking.getCheckIn();
            long difference = checkInDate.getTime() - currentDate.getTime();
            long days = TimeUnit.DAYS.convert(difference, TimeUnit.DAYS);

            if(days >= 7){

                bookingRepository.cancelBooking(request.getBookingId());
                booking = bookingRepository.getById(request.getBookingId());

                if(booking.isCancel()){

                    emailService.notifyBookingCancel(booking.getCustomer().getEmail());

                    return modelToDto(booking);
                }
            }
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
