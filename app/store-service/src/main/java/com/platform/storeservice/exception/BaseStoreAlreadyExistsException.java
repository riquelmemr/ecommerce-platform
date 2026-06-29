package com.platform.storeservice.exception;

public class BaseStoreAlreadyExistsException extends RuntimeException {

    public BaseStoreAlreadyExistsException(String code) {
        super("Base store code already exists: " + code);
    }
}
