package com.jpastudy.bookstudy.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY , mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;


    @Enumerated(STRING)
    private DeliveryStatus status; // READY , COMP
}
