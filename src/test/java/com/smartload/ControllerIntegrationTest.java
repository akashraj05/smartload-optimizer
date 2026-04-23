package com.smartload;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testOptimizeSuccess() throws Exception {

        mockMvc.perform(post("/api/v1/load-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestJson.REQUEST))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.truckId").value("truck-123"))
                .andExpect(jsonPath("$.totalPayoutCents").value(430000))
                .andExpect(jsonPath("$.selectedOrderIds").isArray())
                .andExpect(jsonPath("$.selectedOrderIds.length()").value(2))
                .andExpect(jsonPath("$.totalWeightLbs").value(30000))
                .andExpect(jsonPath("$.totalVolumeCuft").value(2100))
                .andExpect(jsonPath("$.utilizationWeightPercent").exists())
                .andExpect(jsonPath("$.utilizationVolumePercent").exists());
    }

    @Test
    void testInvalidRequestReturns400() throws Exception {

        mockMvc.perform(post("/api/v1/load-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestJson.INVALID_REQUEST))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmptyOrdersHandled() throws Exception {

        String emptyOrdersJson = """
                {
                  "truck": {
                    "id": "truck-empty",
                    "maxWeightLbs": 44000,
                    "maxVolumeCuft": 3000
                  },
                  "orders": []
                }
                """;

        mockMvc.perform(post("/api/v1/load-optimizer/optimize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyOrdersJson))
                .andExpect(status().isBadRequest());
    }
}