package com.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchResponse {

    private long houseId;
    private Date checkIn;
    private Date checkOut;
    private String location;
    private double price;
    private double latitude;
    private double longitude;

}
