package com.smartload.model;

import lombok.Data;

@Data
public class DpResult {
    private int bestMask;
    private long[] weight;
    private long[] volume;
    private long[] payout;

    public DpResult(int bestMask, long[] weight, long[] volume, long[] payout) {
        this.bestMask = bestMask;
        this.weight = weight;
        this.volume = volume;
        this.payout = payout;
    }
}
