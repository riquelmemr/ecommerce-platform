package com.riquelmemr.productservice.data;

import com.riquelmemr.productservice.model.ProductModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPurchaseData {

    private ProductModel product;
    private int quantity;

}
