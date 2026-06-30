CREATE TABLE tb_refresh_token (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    store_id UUID NOT NULL,
    user_type TEXT NOT NULL,
    token TEXT NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    creation_time TIMESTAMP NOT NULL DEFAULT NOW(),
    modified_time TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_refresh_token_token ON tb_refresh_token (token);
CREATE INDEX idx_refresh_token_user ON tb_refresh_token (user_id, store_id);
