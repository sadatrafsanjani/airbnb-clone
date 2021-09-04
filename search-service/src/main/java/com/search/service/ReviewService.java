package com.search.service;

import com.search.dto.request.ReviewRequest;
import com.search.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse saveReview(ReviewRequest request);
    List<ReviewResponse> getReviewsByHouseId(long id);
}
