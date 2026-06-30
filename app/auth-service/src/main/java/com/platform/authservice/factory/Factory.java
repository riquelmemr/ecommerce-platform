package com.platform.authservice.factory;

public interface Factory<T, R> {

    T getObject(R parameter);

}
