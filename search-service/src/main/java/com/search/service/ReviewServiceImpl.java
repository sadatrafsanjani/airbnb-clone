package com.search.service;

import com.search.dto.request.ReviewRequest;
import com.search.dto.response.ReviewResponse;
import com.search.model.Review;
import com.search.repository.HouseRepository;
import com.search.repository.ReviewRepository;
import com.search.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private HouseRepository houseRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             HouseRepository houseRepository,
                             UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.houseRepository = houseRepository;
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
                .house(houseRepository.getById(request.getHouseId()))
                .reviewer(userRepository.getById(request.getReviewerId()))
                .review(request.getReview())
                .stars(request.getStars())
                .build();
    }

    private ReviewResponse modelToDto(Review review){

        return ReviewResponse
                .builder()
                .id(review.getId())
                .houseId(review.getHouse().getId())
                .reviewerId(review.getReviewer().getId())
                .reviewerName(review.getReviewer().getUsername())
                .review(review.getReview())
                .stars(review.getStars())
                .build();

    }
}
