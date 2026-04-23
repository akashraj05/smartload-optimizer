package com.smartload.service;

import com.smartload.model.OptimizeRequest;
import com.smartload.model.OptimizeResponse;

public interface OptimizerService {

    OptimizeResponse optimize(OptimizeRequest optimizeRequest);
}
