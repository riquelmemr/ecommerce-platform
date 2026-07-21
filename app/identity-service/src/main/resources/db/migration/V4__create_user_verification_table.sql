CREATE TABLE tb_user_verification (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES tb_user(id),
    token VARCHAR(255) NOT NULL UNIQUE,
    expiration_date TIMESTAMP NOT NULL,
    creation_time TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_time TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_verification_token ON tb_user_verification (token);
