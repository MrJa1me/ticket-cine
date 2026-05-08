-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c horarios;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS bloqueos_horario;
DROP TABLE IF EXISTS funciones;
DROP TABLE IF EXISTS peliculas_referencia;

-- 2. TABLAS MAESTRAS
CREATE TABLE peliculas_referencia (
    id_peli_ref       INT          PRIMARY KEY,
    titulo            VARCHAR(100) NOT NULL,
    duracion_min      INT          NOT NULL,
    clasificacion     VARCHAR(10)  NOT NULL,
    idioma            VARCHAR(20)  NOT NULL
);

CREATE TABLE funciones (
    id_funcion        SERIAL       PRIMARY KEY,
    id_pelicula       INT          NOT NULL REFERENCES peliculas_referencia(id_peli_ref),
    id_sala           INT          NOT NULL,
    fecha             DATE         NOT NULL,
    hora_inicio       TIME         NOT NULL
);

CREATE TABLE bloqueos_horario (
    id_bloqueo        SERIAL       PRIMARY KEY,
    id_sala           INT          NOT NULL,
    fecha             DATE         NOT NULL,
    motivo            VARCHAR(100) NOT NULL,
    hora_inicio       TIME         NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO peliculas_referencia (id_peli_ref, titulo, duracion_min, clasificacion, idioma) VALUES
(101, 'Amanecer en la Ciudad', 125, 'PG-13', 'Español'),
(102, 'El Último Asiento', 98, 'PG', 'Inglés');

INSERT INTO funciones (id_pelicula, id_sala, fecha, hora_inicio) VALUES
(101, 1, '2026-05-10', '18:30'),
(102, 2, '2026-05-10', '20:45');

INSERT INTO bloqueos_horario (id_sala, fecha, motivo, hora_inicio) VALUES
(1, '2026-05-12', 'Mantenimiento de proyector', '09:00'),
(2, '2026-05-13', 'Limpieza profunda', '12:00');
