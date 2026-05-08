-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c salas;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS mantenimiento_salas;
DROP TABLE IF EXISTS asientos;
DROP TABLE IF EXISTS salas;

-- 2. TABLAS MAESTRAS
CREATE TABLE salas (
    id_sala           SERIAL       PRIMARY KEY,
    nombre            VARCHAR(50)  NOT NULL,
    tipo_proyeccion   VARCHAR(20)  NOT NULL,
    capacidad         INT          NOT NULL,
    estado            VARCHAR(20)  NOT NULL
);

CREATE TABLE asientos (
    id_asiento        SERIAL       PRIMARY KEY,
    id_sala           INT          NOT NULL REFERENCES salas(id_sala),
    fila              CHAR(1)      NOT NULL,
    numero            INT          NOT NULL,
    tipo              VARCHAR(20)  NOT NULL
);

CREATE TABLE mantenimiento_salas (
    id_mantenimiento  SERIAL       PRIMARY KEY,
    id_sala           INT          NOT NULL REFERENCES salas(id_sala),
    fecha_inicio      DATE         NOT NULL,
    descripcion       VARCHAR(200) NOT NULL,
    tecnico_asignado  VARCHAR(100) NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO salas (nombre, tipo_proyeccion, capacidad, estado) VALUES
('Sala 1', '2D', 120, 'Disponible'),
('Sala 2', '3D', 90, 'Disponible');

INSERT INTO asientos (id_sala, fila, numero, tipo) VALUES
(1, 'A', 1, 'Estándar'),
(1, 'A', 2, 'Estándar'),
(2, 'B', 1, 'Premium');

INSERT INTO mantenimiento_salas (id_sala, fecha_inicio, descripcion, tecnico_asignado) VALUES
(2, '2026-05-15', 'Revisión de proyector y sonido', 'Miguel López');
