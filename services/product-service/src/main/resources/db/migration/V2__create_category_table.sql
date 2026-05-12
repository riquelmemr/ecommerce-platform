CREATE TABLE product.tb_category (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

ALTER TABLE product.tb_category
    ADD CONSTRAINT uk_category_code UNIQUE (code);