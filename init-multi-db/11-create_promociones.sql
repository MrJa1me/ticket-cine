-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c promociones;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS puntos_fidelidad;
DROP TABLE IF EXISTS cupones;
DROP TABLE IF EXISTS campañas;

-- 2. TABLAS MAESTRAS
CREATE TABLE campañas (
    id_campaña             SERIAL       PRIMARY KEY,
    nombre                 VARCHAR(100) NOT NULL,
    fecha_inicio           DATE         NOT NULL,
    fecha_fin              DATE         NOT NULL,
    porcentaje_descuento   INT          NOT NULL
);

CREATE TABLE cupones (
    id_cupon               SERIAL       PRIMARY KEY,
    id_campaña             INT          NOT NULL REFERENCES campañas(id_campaña),
    codigo_unico           VARCHAR(20)  NOT NULL,
    stock_disponible       INT          NOT NULL,
    activo                 BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE puntos_fidelidad (
    id_punto               SERIAL       PRIMARY KEY,
    id_usuario             INT          NOT NULL,
    cantidad_puntos        INT          NOT NULL,
    ultima_actualizacion   TIMESTAMP    NOT NULL DEFAULT NOW(),
    puntos_por_vencer      INT          NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO campañas (nombre, fecha_inicio, fecha_fin, porcentaje_descuento) VALUES
('Promo Verano', '2026-06-01', '2026-06-30', 20);

INSERT INTO cupones (id_campaña, codigo_unico, stock_disponible, activo) VALUES
(1, 'VERANO2026', 100, TRUE);

INSERT INTO puntos_fidelidad (id_usuario, cantidad_puntos, puntos_por_vencer) VALUES
(7, 200, 50);
