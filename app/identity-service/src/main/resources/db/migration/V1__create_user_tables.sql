CREATE TABLE tb_user (
    id UUID PRIMARY KEY,
    store_id UUID,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(150) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_employee BOOLEAN NOT NULL DEFAULT FALSE,
    creation_time TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_time TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE tb_customer (
    id UUID PRIMARY KEY REFERENCES tb_user(id)
);

CREATE TABLE tb_employee (
    id UUID PRIMARY KEY REFERENCES tb_user(id)
);

CREATE UNIQUE INDEX idx_user_email_store ON tb_user (email, store_id) WHERE store_id IS NOT NULL;
CREATE UNIQUE INDEX idx_user_email_global ON tb_user (email) WHERE store_id IS NULL;
CREATE INDEX idx_user_store ON tb_user (store_id);
