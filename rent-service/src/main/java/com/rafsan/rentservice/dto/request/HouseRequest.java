package com.rafsan.rentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HouseRequest {

    private long hostId;
    private String title;
    private String description;
    private String location;
    private double price;
    private double latitude;
    private double longitude;
}
