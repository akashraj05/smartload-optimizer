package com.smartload.util;

import com.smartload.model.Order;

import java.time.LocalDate;
import java.util.List;

public class UtilizationUtils {

    public static double percent(long value, long max) {
        if (max == 0) return 0.0;
        return Math.round((value * 10000.0) / max) / 100.0;
    }

    public static LocalDate max(LocalDate a, LocalDate b) {
        return (a.isAfter(b)) ? a : b;
    }

    public static LocalDate min(LocalDate a, LocalDate b) {
        return (a.isBefore(b)) ? a : b;
    }

    public static List<Order> preFilterOrders(List<Order> orders) {

        if (orders == null || orders.isEmpty()) return List.of();

        String origin = orders.getFirst().getOrigin();
        String destination = orders.getFirst().getDestination();

        return orders.stream()
                .filter(o -> o.getOrigin().equals(origin)
                        && o.getDestination().equals(destination))
                .toList();
    }
}