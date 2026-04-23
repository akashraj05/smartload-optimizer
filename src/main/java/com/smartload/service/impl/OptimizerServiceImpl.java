package com.smartload.service.impl;

import com.smartload.model.*;
import com.smartload.service.OptimizerService;
import com.smartload.util.BitmaskUtils;
import com.smartload.util.UtilizationUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OptimizerServiceImpl implements OptimizerService {

    public OptimizeResponse optimize(OptimizeRequest req) {

        List<Order> orders = UtilizationUtils.preFilterOrders(req.getOrders());

        if (orders.isEmpty()) {
            return getOptimizeResponse(req);
        }

        DpResult result = runDpOptimization(orders, req.getTruck());

        if (result.getBestMask() == 0) {
            return getOptimizeResponse(req);
        }

        return buildResponse(result.getBestMask(), orders, req, result);
    }

    private static OptimizeResponse getOptimizeResponse(OptimizeRequest req) {
        return OptimizeResponse.empty(req.getTruck().getId());
    }

    private DpResult runDpOptimization(List<Order> orders, Truck truck) {

        int totalMasks = 1 << orders.size();

        long maxW = truck.getMaxWeightLbs();
        long maxV = truck.getMaxVolumeCuft();

        long[] weight = new long[totalMasks];
        long[] volume = new long[totalMasks];
        long[] payout = new long[totalMasks];

        boolean[] hasHaz = new boolean[totalMasks];
        boolean[] hasNonHaz = new boolean[totalMasks];

        LocalDate[] maxPickup = new LocalDate[totalMasks];
        LocalDate[] minDelivery = new LocalDate[totalMasks];

        long bestPayout = 0;
        int bestMask = 0;

        for (int mask = 1; mask < totalMasks; mask++) {

            int lsb = mask & -mask;
            int idx = Integer.numberOfTrailingZeros(lsb);
            int prev = mask ^ lsb;

            Order order = orders.get(idx);

            // weight
            weight[mask] = weight[prev] + order.getWeightLbs();
            if (weight[mask] > maxW) continue;

            // volume
            volume[mask] = volume[prev] + order.getVolumeCuft();
            if (volume[mask] > maxV) continue;

            // payout
            payout[mask] = payout[prev] + order.getPayoutCents();

            // hazmat
            hasHaz[mask] = hasHaz[prev] || order.isHazmat();
            hasNonHaz[mask] = hasNonHaz[prev] || !order.isHazmat();
            if (hasHaz[mask] && hasNonHaz[mask]) continue;

            // time window
            if (prev == 0) {
                maxPickup[mask] = order.getPickupDate();
                minDelivery[mask] = order.getDeliveryDate();
            } else {
                maxPickup[mask] = UtilizationUtils.max(maxPickup[prev], order.getPickupDate());
                minDelivery[mask] = UtilizationUtils.min(minDelivery[prev], order.getDeliveryDate());
            }

            if (maxPickup[mask].isAfter(minDelivery[mask])) continue;

            // best
            if (payout[mask] > bestPayout) {
                bestPayout = payout[mask];
                bestMask = mask;
            }
        }

        return new DpResult(bestMask, weight, volume, payout);
    }

    private OptimizeResponse buildResponse(
            int mask,
            List<Order> orders,
            OptimizeRequest req,
            DpResult result
    ) {
        OptimizeResponse optimizeResponse = new OptimizeResponse();

        optimizeResponse.setTruckId(req.getTruck().getId());

        // Extract selected order IDs
        List<String> selectedIds = new ArrayList<>();
        for (int idx : BitmaskUtils.getSetBitIndices(mask)) {
            selectedIds.add(orders.get(idx).getId());
        }

        optimizeResponse.setSelectedOrderIds(selectedIds);

        long totalWeight = result.getWeight()[mask];
        long totalVolume = result.getVolume()[mask];
        long totalPayout = result.getPayout()[mask];

        optimizeResponse.setTotalWeightLbs(totalWeight);
        optimizeResponse.setTotalVolumeCuft(totalVolume);
        optimizeResponse.setTotalPayoutCents(totalPayout);

        // Utilization (rounded to 2 decimal places)
        optimizeResponse.setUtilizationWeightPercent(
                UtilizationUtils.percent(totalWeight, req.getTruck().getMaxWeightLbs())
        );

        optimizeResponse.setUtilizationVolumePercent(
                UtilizationUtils.percent(totalVolume, req.getTruck().getMaxVolumeCuft())
        );

        return optimizeResponse;
    }
}
