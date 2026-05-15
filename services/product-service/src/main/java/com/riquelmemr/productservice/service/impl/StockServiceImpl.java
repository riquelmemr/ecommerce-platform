package com.riquelmemr.productservice.service.impl;

import com.riquelmemr.productservice.exception.InsufficientStockException;
import com.riquelmemr.productservice.model.ProductModel;
import com.riquelmemr.productservice.repository.ProductRepository;
import com.riquelmemr.productservice.service.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void reserveProduct(ProductModel product, int quantity) {
        int availableAmount = getAvailableAmount(product);

        if (availableAmount < quantity) {
            throw new InsufficientStockException(product.getId(), quantity, availableAmount);
        }

        product.setReserved(product.getReserved() + quantity);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void confirmReservation(ProductModel product, int quantity) {
        int availableAmount = getAvailableAmount(product);

        if (availableAmount < quantity) {
            throw new IllegalStateException("Reserved stock  is lower than confirmation quantity");
        }

        product.setReserved(product.getReserved() - quantity);
        product.setStock(product.getStock() - quantity);

        productRepository.save(product);
    }

    @Override
    public void releaseReservation(ProductModel product, int quantity) {
        if (product.getReserved() < quantity) {
            throw new IllegalStateException(
                    "Reserved stock is lower than release quantity"
            );
        }

        product.setReserved(product.getReserved() - quantity);
        productRepository.save(product);
    }

    @Override
    public int getAvailableAmount(ProductModel product) {
        return product.getStock() - product.getReserved();
    }
}
