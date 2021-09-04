package com.search.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchRequest {

    private long houseId;
    private String location;
    private double minPrice;
    private double maxPrice;
    private double latitude;
    private double longitude;

}
