package com.platform.storeservice.controller;

import com.platform.storeservice.dto.request.BaseStoreRequest;
import com.platform.storeservice.dto.request.BaseStoreUpdateRequest;
import com.platform.storeservice.dto.response.BaseStoreResponse;
import com.platform.storeservice.service.BaseStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class BaseStoreController {

    private final BaseStoreService baseStoreService;

    @PostMapping
    public ResponseEntity<BaseStoreResponse> create(@Valid @RequestBody BaseStoreRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(baseStoreService.create(request));
    }

    @GetMapping("/{code}")
    public ResponseEntity<BaseStoreResponse> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(baseStoreService.findByCode(code));
    }

    @GetMapping
    public ResponseEntity<List<BaseStoreResponse>> findAll() {
        return ResponseEntity.ok(baseStoreService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseStoreResponse> update(@PathVariable UUID id, @RequestBody BaseStoreUpdateRequest request) {
        return ResponseEntity.ok(baseStoreService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        baseStoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
