package com.smartload.controller;

import com.smartload.model.OptimizeRequest;
import com.smartload.model.OptimizeResponse;
import com.smartload.service.OptimizerService;
import com.smartload.validation.RequestValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/load-optimizer")
public class LoadOptimizerController {

    private final OptimizerService optimizerService;
    private final RequestValidator validator;

    public LoadOptimizerController(OptimizerService optimizerService, RequestValidator requestValidator) {
        this.optimizerService = optimizerService;
        this.validator = requestValidator;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizeResponse> optimize(@RequestBody OptimizeRequest req) {
        validator.validate(req);
        return ResponseEntity.ok(optimizerService.optimize(req));
    }
}