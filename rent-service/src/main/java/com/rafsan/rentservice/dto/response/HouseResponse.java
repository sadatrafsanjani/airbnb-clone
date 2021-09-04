package com.rafsan.rentservice.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HouseResponse {

    private long id;
    private long hostId;
    private String hostName;
    private String title;
    private String description;
    private String location;
    private double price;
    private double latitude;
    private double longitude;
    private boolean status;
    private boolean rejection;
}
