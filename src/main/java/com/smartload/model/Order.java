package com.smartload.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Order {

    private String id;
    private long payoutCents;
    private long weightLbs;
    private long volumeCuft;
    private String origin;
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickupDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    private boolean isHazmat;
}