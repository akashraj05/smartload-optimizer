package com.smartload.service.impl;

import com.smartload.model.Order;
import com.smartload.service.CompatibilityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompatibilityServiceImpl implements CompatibilityService {

    public boolean isRouteCompatible(List<Order> orders) {
        String origin = orders.getFirst().getOrigin();
        String dest = orders.getFirst().getDestination();

        return orders.stream().allMatch(o ->
                o.getOrigin().equals(origin) &&
                        o.getDestination().equals(dest)
        );
    }

    public boolean isTimeCompatible(List<Order> orders) {
        LocalDate maxPickup = orders.stream()
                .map(Order::getPickupDate)
                .max(LocalDate::compareTo).get();

        LocalDate minDelivery = orders.stream()
                .map(Order::getDeliveryDate)
                .min(LocalDate::compareTo).get();

        return !maxPickup.isAfter(minDelivery);
    }
}
