CREATE TABLE tb_address (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES tb_user(id),
    store_id UUID NOT NULL,
    name VARCHAR(150),
    street VARCHAR(255) NOT NULL,
    number VARCHAR(20) NOT NULL,
    complement VARCHAR(100),
    neighborhood VARCHAR(150) NOT NULL,
    city VARCHAR(150) NOT NULL,
    state VARCHAR(2) NOT NULL,
    postal_code VARCHAR(10) NOT NULL,
    country VARCHAR(100) NOT NULL DEFAULT 'Brasil',
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    creation_time TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_time TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_address_user ON tb_address (user_id);
