package com.smartload.service;

import com.smartload.model.Order;

import java.util.List;

public interface CompatibilityService {

    boolean isRouteCompatible(List<Order> orders);

    boolean isTimeCompatible(List<Order> orders);
}
