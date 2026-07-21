package com.platform.identityservice.client;

import com.platform.identityservice.dto.store.BaseStoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "store-service", path = "/api/v1/stores")
public interface StoreClient {

    @GetMapping("/{code}")
    BaseStoreResponse findByCode(@PathVariable String code);

}
