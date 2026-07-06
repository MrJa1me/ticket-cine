-- =========================================
-- 03-catalogo.sql
-- =========================================

\c catalogo;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS Peliculas;
DROP TABLE IF EXISTS generos;
DROP TABLE IF EXISTS categorias;

-- 2. CREACIÓN DE TABLAS MAESTRAS
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(50),
    descripcion VARCHAR(100),
    estado VARCHAR(20),
    fecha_creacion DATE
);

CREATE TABLE generos (
    id_genero SERIAL PRIMARY KEY,
    nombre VARCHAR(50),
    descripcion VARCHAR(100),
    popularidad INT,
    estado VARCHAR(20)
);

CREATE TABLE Peliculas (
    id_Pelicula SERIAL PRIMARY KEY,
    nombre_Pelicula VARCHAR(100),
    categoria VARCHAR(50),
    fecha DATE,
    estado VARCHAR(20),
    idioma VARCHAR(20),
    duracion_minutos INT,
    clasificacion VARCHAR(10),
    id_genero INT,
    sinopsis TEXT
);

-- 4. INSERCIÓN DE DATOS (Poblamiento)

-- Categorías de Películas
INSERT INTO categorias (nombre, descripcion, estado, fecha_creacion) VALUES
('Estreno', 'Películas recién llegadas a cartelera', 'Activo', '2025-01-10'),
('Clasicos', 'Películas emblemáticas que marcaron época', 'Activo', '2025-01-15'),
('Animacion', 'Películas animadas para toda la familia', 'Activo', '2025-02-01'),
('Independiente', 'Producciones independientes y de autor', 'Activo', '2025-03-20');

-- Géneros de Películas
INSERT INTO generos (nombre, descripcion, popularidad, estado) VALUES
('Ciencia Ficcion', 'Ficción especulativa basada en avances científicos o tecnológicos', 90, 'Activo'),
('Drama', 'Narrativa seria y realista centrada en el desarrollo de personajes', 85, 'Activo'),
('Animacion', 'Películas creadas mediante técnicas de animación', 88, 'Activo'),
('Accion', 'Secuencias dinámicas con persecuciones, combates y efectos visuales', 92, 'Activo'),
('Comedia', 'Obras diseñadas para provocar risa y entretenimiento', 80, 'Activo'),
('Terror', 'Narrativa que busca provocar miedo, suspenso o repulsión', 75, 'Activo');

-- Catálogo de Películas (La oferta disponible para el usuario)
INSERT INTO Peliculas (nombre_Pelicula, categoria, fecha, estado, idioma, duracion_minutos, clasificacion, id_genero) VALUES
('Dune: Parte Dos', 'Estreno', '2025-12-20', 'Disponible', 'Español', 166, 'PG-13', 1),
('Oppenheimer', 'Estreno', '2025-11-15', 'Disponible', 'Ingles Subtitulada', 180, 'R', 2),
('Intensamente 2', 'Estreno', '2025-10-01', 'Disponible', 'Español', 96, 'PG', 3),
('Deadpool & Wolverine', 'Estreno', '2026-03-15', 'Preventa', 'Ingles Subtitulada', 128, 'R', 4);
