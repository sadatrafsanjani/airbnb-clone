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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "HOUSE_ID")
    private House house;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "REVIEWER_ID")
    private User reviewer;

    @Column(name = "CHECK_IN")
    private String review;

    @Column(name = "STARTS")
    private int stars;
}
