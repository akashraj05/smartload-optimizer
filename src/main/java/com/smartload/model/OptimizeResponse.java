package com.smartload.model;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class OptimizeResponse {

    private String truckId;
    private List<String> selectedOrderIds;
    private long totalPayoutCents;
    private long totalWeightLbs;
    private long totalVolumeCuft;
    private double utilizationWeightPercent;
    private double utilizationVolumePercent;

    public static OptimizeResponse empty(String truckId) {
        OptimizeResponse optimizeResponse = new OptimizeResponse();
        optimizeResponse.truckId = truckId;
        optimizeResponse.selectedOrderIds = Collections.emptyList();
        return optimizeResponse;
    }


}