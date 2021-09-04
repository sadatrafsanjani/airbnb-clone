package com.search.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CUSTOMER_ID")
    private User customer;

    @Column(name = "CHECK_IN")
    private Date checkIn;

    @Column(name = "CHECK_OUT")
    private Date checkOut;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "REJECTION")
    private boolean rejection;

    @Column(name = "CANCEL")
    private boolean cancel;
}
