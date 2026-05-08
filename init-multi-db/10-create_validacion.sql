-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c validacion;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS incidencias;
DROP TABLE IF EXISTS accesos_log;
DROP TABLE IF EXISTS tickets_activos;

-- 2. TABLAS MAESTRAS
CREATE TABLE tickets_activos (
    id_ticket         SERIAL       PRIMARY KEY,
    id_reserva        INT          NOT NULL,
    codigo_qr         VARCHAR(255) NOT NULL,
    fecha_emision     TIMESTAMP    NOT NULL DEFAULT NOW(),
    usado             BOOLEAN      DEFAULT FALSE
);

CREATE TABLE accesos_log (
    id_acceso         SERIAL       PRIMARY KEY,
    id_ticket         INT          NOT NULL REFERENCES tickets_activos(id_ticket),
    fecha_entrada     TIMESTAMP    NOT NULL,
    puerta_ingreso    VARCHAR(20)  NOT NULL,
    resultado         VARCHAR(20)  NOT NULL
);

CREATE TABLE incidencias (
    id_incidencia     SERIAL       PRIMARY KEY,
    id_ticket         INT          NOT NULL REFERENCES tickets_activos(id_ticket),
    descripcion       TEXT         NOT NULL,
    nivel_gravedad    INT          NOT NULL,
    resuelto          BOOLEAN      DEFAULT FALSE
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO tickets_activos (id_reserva, codigo_qr) VALUES
(1, 'QR-ABCDEFG123456');

INSERT INTO accesos_log (id_ticket, fecha_entrada, puerta_ingreso, resultado) VALUES
(1, '2026-05-10 18:25:00', 'Puerta 1', 'Permitido');

INSERT INTO incidencias (id_ticket, descripcion, nivel_gravedad, resuelto) VALUES
(1, 'El código QR tardó en leerse.', 2, TRUE);
