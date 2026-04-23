package com.smartload;

import com.smartload.model.OptimizeRequest;
import com.smartload.model.OptimizeResponse;
import com.smartload.service.impl.OptimizerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OptimizerServiceImplTest {

    @Autowired
    private OptimizerServiceImpl service;

    @Test
    void testOptimalSelection() {

        OptimizeRequest req = TestData.sampleRequest();

        OptimizeResponse res = service.optimize(req);

        assertEquals(430000, res.getTotalPayoutCents());
        assertTrue(res.getSelectedOrderIds().contains("ord-001"));
    }
}
