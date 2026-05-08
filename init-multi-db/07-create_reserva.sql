-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c reserva;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS temporal_apartado;
DROP TABLE IF EXISTS reserva_detalle;
DROP TABLE IF EXISTS reservas;

-- 2. TABLAS MAESTRAS
CREATE TABLE reservas (
    id_reserva        SERIAL       PRIMARY KEY,
    id_usuario        INT          NOT NULL,
    id_funcion        INT          NOT NULL,
    fecha_reserva     TIMESTAMP    NOT NULL DEFAULT NOW(),
    estado_reserva    VARCHAR(20)  NOT NULL
);

CREATE TABLE reserva_detalle (
    id_detalle        SERIAL       PRIMARY KEY,
    id_reserva        INT          NOT NULL REFERENCES reservas(id_reserva),
    id_asiento        INT          NOT NULL,
    precio_unitario   DECIMAL      NOT NULL,
    subtotal          DECIMAL      NOT NULL
);

CREATE TABLE temporal_apartado (
    id_temporal       SERIAL       PRIMARY KEY,
    id_asiento        INT          NOT NULL,
    id_funcion        INT          NOT NULL,
    expiracion        TIMESTAMP    NOT NULL,
    uuid_sesion       VARCHAR(50)  NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO reservas (id_usuario, id_funcion, fecha_reserva, estado_reserva) VALUES
(7, 1, '2026-05-01 10:30:00', 'Confirmada');

INSERT INTO reserva_detalle (id_reserva, id_asiento, precio_unitario, subtotal) VALUES
(1, 1, 8.50, 8.50);

INSERT INTO temporal_apartado (id_asiento, id_funcion, expiracion, uuid_sesion) VALUES
(2, 1, '2026-05-01 10:45:00', 'session-1234-uuid');
