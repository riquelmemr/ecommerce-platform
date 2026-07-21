CREATE TABLE tb_role (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tb_user_roles (
    user_id UUID NOT NULL REFERENCES tb_user(id),
    role_id UUID NOT NULL REFERENCES tb_role(id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO tb_role (id, name) VALUES (gen_random_uuid(), 'CUSTOMER');
INSERT INTO tb_role (id, name) VALUES (gen_random_uuid(), 'STORE_EMPLOYEE');
INSERT INTO tb_role (id, name) VALUES (gen_random_uuid(), 'PLATFORM_ADMIN');
