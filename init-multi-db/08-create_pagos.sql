-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c pagos;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS logs_pasarela;
DROP TABLE IF EXISTS comprobantes;
DROP TABLE IF EXISTS transacciones;

-- 2. TABLAS MAESTRAS
CREATE TABLE transacciones (
    id_pago           SERIAL       PRIMARY KEY,
    id_reserva        INT          NOT NULL,
    monto             DECIMAL      NOT NULL,
    metodo            VARCHAR(30)  NOT NULL,
    fecha_pago        TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE comprobantes (
    id_comprobante    SERIAL       PRIMARY KEY,
    id_pago           INT          NOT NULL REFERENCES transacciones(id_pago),
    serie             VARCHAR(10)  NOT NULL,
    numero            VARCHAR(20)  NOT NULL,
    ruc_dni           VARCHAR(15)  NOT NULL
);

CREATE TABLE logs_pasarela (
    id_log            SERIAL       PRIMARY KEY,
    id_pago           INT          NOT NULL REFERENCES transacciones(id_pago),
    codigo_api        VARCHAR(50)  NOT NULL,
    mensaje_respuesta TEXT         NOT NULL,
    estado_api        VARCHAR(20)  NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO transacciones (id_reserva, monto, metodo, fecha_pago) VALUES
(1, 8.50, 'Tarjeta', '2026-05-01 10:35:00');

INSERT INTO comprobantes (id_pago, serie, numero, ruc_dni) VALUES
(1, 'F001', '00012345', '12345678-9');

INSERT INTO logs_pasarela (id_pago, codigo_api, mensaje_respuesta, estado_api) VALUES
(1, 'PAY-2026', 'Pago aprobado', 'OK');
