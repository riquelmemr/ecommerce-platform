package com.platform.storeservice.exception;

import java.util.UUID;

public class BaseStoreNotFoundException extends RuntimeException {

    public BaseStoreNotFoundException(UUID id) {
        super("Base store not found: " + id);
    }

    public BaseStoreNotFoundException(String msg) {
        super(msg);
    }
}
