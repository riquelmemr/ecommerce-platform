CREATE INDEX idx_product_categories_product
    ON product.tb_product_categories(product_id);

CREATE INDEX idx_product_categories_category
    ON product.tb_product_categories(category_id);