package com.search.service;

import com.search.dto.request.ReviewRequest;
import com.search.dto.response.ReviewResponse;
import com.search.model.Review;
import com.search.repository.BookingRepository;
import com.search.repository.ReviewRepository;
import com.search.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private BookingRepository bookingRepository;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(BookingRepository bookingRepository,
                             ReviewRepository reviewRepository,
                             UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewResponse saveReview(ReviewRequest request){

        return modelToDto(reviewRepository.save(dtoToModel(request)));
    }

    @Override
    public List<ReviewResponse> getReviewsByHouseId(long id){

        List<ReviewResponse> responses = new ArrayList<>();

        for(Review review : reviewRepository.findByHouseId(id)){

            responses.add(modelToDto(review));
        }

        return responses;
    }

    private Review dtoToModel(ReviewRequest request){

        return Review
                .builder()
                .booking(bookingRepository.getById(request.getBookingId()))
                .reviewer(userRepository.getById(request.getReviewerId()))
                .review(request.getReview())
                .stars(request.getStars())
                .build();
    }

    private ReviewResponse modelToDto(Review review){

        return ReviewResponse
                .builder()
                .id(review.getId())
                .houseId(review.getBooking().getHouse().getId())
                .bookingId(review.getBooking().getId())
                .reviewerId(review.getReviewer().getId())
                .reviewerName(review.getReviewer().getUsername())
                .review(review.getReview())
                .stars(review.getStars())
                .build();

    }
}
