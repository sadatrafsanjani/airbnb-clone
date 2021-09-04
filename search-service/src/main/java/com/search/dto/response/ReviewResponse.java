package com.search.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewResponse {

    private Long id;
    private long houseId;
    private long reviewerId;
    private String reviewerName;
    private String review;
    private int stars;
}
