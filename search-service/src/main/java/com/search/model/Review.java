package com.search.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BOOKING_ID")
    private Booking booking;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "REVIEWER_ID")
    private User reviewer;

    @Column(name = "REVIEW")
    private String review;

    @Column(name = "STARS")
    private int stars;
}
