package com.platform.storeservice.controller;

import com.platform.storeservice.dto.response.BaseStoreResponse;
import com.platform.storeservice.service.BaseStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/internal/stores")
@RequiredArgsConstructor
public class BaseStoreInternalController {

    private final BaseStoreService baseStoreService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseStoreResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(baseStoreService.findById(id));
    }

}
