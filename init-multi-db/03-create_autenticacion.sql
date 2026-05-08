-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c autenticacion;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS roles_cuenta;
DROP TABLE IF EXISTS sesiones;
DROP TABLE IF EXISTS cuentas;

-- 2. TABLAS MAESTRAS
CREATE TABLE cuentas (
    id_cuenta         SERIAL       PRIMARY KEY,
    email             VARCHAR(100) UNIQUE NOT NULL,
    password_hash     VARCHAR(255) NOT NULL,
    fecha_creacion    TIMESTAMP    NOT NULL DEFAULT NOW(),
    estado            BOOLEAN      DEFAULT TRUE
);

CREATE TABLE sesiones (
    id_sesion         SERIAL       PRIMARY KEY,
    id_cuenta         INT          NOT NULL REFERENCES cuentas(id_cuenta),
    token_jwt         TEXT         NOT NULL,
    fecha_expiracion  TIMESTAMP    NOT NULL,
    ip_origen         VARCHAR(45)  NOT NULL
);

CREATE TABLE roles_cuenta (
    id_rol_cta        SERIAL       PRIMARY KEY,
    id_cuenta         INT          NOT NULL REFERENCES cuentas(id_cuenta),
    nombre_rol        VARCHAR(20)  NOT NULL CHECK (nombre_rol IN ('ADMIN', 'CLIENTE', 'OPERADOR')),
    fecha_asignacion  DATE         NOT NULL,
    asignado_por      VARCHAR(50)  NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO cuentas (email, password_hash, fecha_creacion, estado) VALUES
('admin@cine.cl',  '$2a$12$EXAMPLEADMINHASH1234567890abcdefghijklmnopqrstuv', NOW(), TRUE),
('cliente@cine.cl', '$2a$12$EXAMPLECLIENTHASH1234567890abcdefghijklmnopqr', NOW(), TRUE),
('operador@cine.cl','$2a$12$EXAMPLEOPERADORHASH1234567890abcdefghijklmnop', NOW(), TRUE);

INSERT INTO roles_cuenta (id_cuenta, nombre_rol, fecha_asignacion, asignado_por) VALUES
(1, 'ADMIN',   CURRENT_DATE, 'sistema'),
(2, 'CLIENTE', CURRENT_DATE, 'sistema'),
(3, 'OPERADOR',CURRENT_DATE, 'sistema');
