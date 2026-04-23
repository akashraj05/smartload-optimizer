package com.smartload.model;

import lombok.Data;

@Data
public class Truck {

    private String id;
    private long maxWeightLbs;
    private long maxVolumeCuft;
}