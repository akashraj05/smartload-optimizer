package com.smartload.validation;

import com.smartload.exception.BadRequestException;
import com.smartload.model.OptimizeRequest;
import com.smartload.model.Order;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    public void validate(OptimizeRequest req) {

        if (req.getTruck() == null)
            throw new BadRequestException("Truck is required");

        if (req.getOrders() == null || req.getOrders().isEmpty())
            throw new BadRequestException("Orders cannot be empty");

        if (req.getOrders().size() > 22)
            throw new BadRequestException("Too many orders (max 22)");

        for (Order o : req.getOrders()) {
            if (o.getPickupDate().isAfter(o.getDeliveryDate())) {
                throw new BadRequestException("Invalid date window: " + o.getId());
            }
        }
    }
}
