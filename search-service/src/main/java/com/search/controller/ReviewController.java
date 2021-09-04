package com.search.controller;

import com.search.dto.request.ReviewRequest;
import com.search.dto.response.ReviewResponse;
import com.search.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    ResponseEntity<ReviewResponse> saveReview(@RequestBody ReviewRequest request){

        ReviewResponse response = reviewService.saveReview(request);

        if(response != null){

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/findReviewsByHouseId/{id}")
    ResponseEntity<List<ReviewResponse>> findReviewsByHouseId(@PathVariable("id") long id){

        List<ReviewResponse> responses = reviewService.getReviewsByHouseId(id);

        if(!responses.isEmpty()){

            return ResponseEntity.ok(responses);
        }

        return ResponseEntity.ok().build();
    }

}
