package com.riquelmemr.productservice.service;

import com.riquelmemr.productservice.model.ProductModel;

public interface StockService {

    void reserveProduct(ProductModel product, int quantity);

    void confirmReservation(ProductModel product, int quantity);

    void releaseReservation(ProductModel product, int quantity);

    int getAvailableAmount(ProductModel product);

}
