package com.rafsan.rentservice.dto.response;

import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponse {

    private long id;
    private long houseId;
    private long customerId;
    private Date checkIn;
    private Date checkOut;
    private boolean status;
    private boolean rejection;
    private boolean cancel;
}
