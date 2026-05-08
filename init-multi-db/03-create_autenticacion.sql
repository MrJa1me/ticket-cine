\c auth_db

DROP TABLE IF EXISTS bitacora_sesiones;
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS credenciales;

CREATE TABLE credenciales (
    user_email VARCHAR(100) PRIMARY KEY,
    pass_hash VARCHAR(255),
    mfa_habilitado BOOLEAN DEFAULT FALSE
);

CREATE TABLE tokens (
    id_token SERIAL PRIMARY KEY,
    user_email VARCHAR(100) REFERENCES credenciales(user_email),
    jwt_secret TEXT,
    expira_at TIMESTAMP
);

CREATE TABLE bitacora_sesiones (
    id_sesion SERIAL PRIMARY KEY,
    id_token INTEGER REFERENCES tokens(id_token),
    ip_origen VARCHAR(45),
    dispositivo VARCHAR(50)
);

INSERT INTO credenciales VALUES ('u1@test.com','hash1',false),('u2@test.com','hash2',false),('u3@test.com','hash3',false),('u4@test.com','hash4',false),('u5@test.com','hash5',false),('u6@test.com','hash6',false),('u7@test.com','hash7',false),('u8@test.com','hash8',false),('u9@test.com','hash9',false);
INSERT INTO tokens (user_email, jwt_secret, expira_at) SELECT user_email, 'secret', '2026-12-31' FROM credenciales;
INSERT INTO bitacora_sesiones (id_token, ip_origen, dispositivo) SELECT id_token, '127.0.0.1', 'Mobile' FROM tokens;