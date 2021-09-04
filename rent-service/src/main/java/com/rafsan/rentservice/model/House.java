package com.rafsan.rentservice.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "houses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name= "HOST_ID")
    @ManyToOne(cascade = CascadeType.MERGE)
    private User host;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "REJECTION")
    private boolean rejection;
}
