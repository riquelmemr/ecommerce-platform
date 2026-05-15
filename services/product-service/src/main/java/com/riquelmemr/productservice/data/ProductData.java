package com.riquelmemr.productservice.data;

import com.riquelmemr.productservice.dto.ProductRequest;
import com.riquelmemr.productservice.model.CategoryModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductData {

    private Long id;
    private String name;
    private String description;
    private int stock;
    private int reserved;
    private BigDecimal price;
    private Set<CategoryModel> categories;

    public ProductData(ProductRequest request) {
        BeanUtils.copyProperties(request, this);
    }
}
