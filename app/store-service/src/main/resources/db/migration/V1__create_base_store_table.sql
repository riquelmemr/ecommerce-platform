CREATE TABLE tb_base_store (
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    logo_url VARCHAR(500),
    primary_color VARCHAR(20),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    domain VARCHAR(255),
    creation_time TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_time TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_base_store_code ON tb_base_store (code);
