package com.smartload;

import com.smartload.model.*;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static OptimizeRequest sampleRequest() {

        Truck truck = new Truck();
        truck.setId("truck-123");
        truck.setMaxWeightLbs(44000);
        truck.setMaxVolumeCuft(3000);

        Order o1 = new Order();
        o1.setId("ord-001");
        o1.setPayoutCents(250000);
        o1.setWeightLbs(18000);
        o1.setVolumeCuft(1200);
        o1.setOrigin("Los Angeles, CA");
        o1.setDestination("Dallas, TX");
        o1.setPickupDate(LocalDate.of(2025, 12, 5));
        o1.setDeliveryDate(LocalDate.of(2025, 12, 9));
        o1.setHazmat(false);

        Order o2 = new Order();
        o2.setId("ord-002");
        o2.setPayoutCents(180000);
        o2.setWeightLbs(12000);
        o2.setVolumeCuft(900);
        o2.setOrigin("Los Angeles, CA");
        o2.setDestination("Dallas, TX");
        o2.setPickupDate(LocalDate.of(2025, 12, 4));
        o2.setDeliveryDate(LocalDate.of(2025, 12, 10));
        o2.setHazmat(false);

        Order o3 = new Order();
        o3.setId("ord-003");
        o3.setPayoutCents(320000);
        o3.setWeightLbs(30000);
        o3.setVolumeCuft(1800);
        o3.setOrigin("Los Angeles, CA");
        o3.setDestination("Dallas, TX");
        o3.setPickupDate(LocalDate.of(2025, 12, 6));
        o3.setDeliveryDate(LocalDate.of(2025, 12, 8));
        o3.setHazmat(true);

        OptimizeRequest req = new OptimizeRequest();
        req.setTruck(truck);
        req.setOrders(List.of(o1, o2, o3));

        return req;
    }

    // Edge case: no valid combination (overweight)
    public static OptimizeRequest overweightRequest() {
        OptimizeRequest req = sampleRequest();
        req.getTruck().setMaxWeightLbs(10000);
        return req;
    }

    // Edge case: empty orders
    public static OptimizeRequest emptyRequest() {
        OptimizeRequest req = new OptimizeRequest();

        Truck truck = new Truck();
        truck.setId("truck-empty");
        truck.setMaxWeightLbs(44000);
        truck.setMaxVolumeCuft(3000);

        req.setTruck(truck);
        req.setOrders(List.of());

        return req;
    }
}
