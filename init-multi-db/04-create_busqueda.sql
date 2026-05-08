-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c busqueda;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS historial_busquedas;
DROP TABLE IF EXISTS etiquetas;
DROP TABLE IF EXISTS catalogo_peliculas;

-- 2. TABLAS MAESTRAS
CREATE TABLE catalogo_peliculas (
    id_peli_busq      SERIAL       PRIMARY KEY,
    titulo            VARCHAR(100) NOT NULL,
    genero            VARCHAR(50)  NOT NULL,
    sinopsis          TEXT         NOT NULL,
    popularidad       DECIMAL      DEFAULT 0
);

CREATE TABLE etiquetas (
    id_etiqueta       SERIAL       PRIMARY KEY,
    nombre            VARCHAR(30)  NOT NULL,
    categoria         VARCHAR(30)  NOT NULL,
    descripcion       VARCHAR(100),
    estado            BOOLEAN      DEFAULT TRUE
);

CREATE TABLE historial_busquedas (
    id_busqueda             SERIAL    PRIMARY KEY,
    id_usuario              INT       NOT NULL,
    termino                VARCHAR(100) NOT NULL,
    fecha_hora             TIMESTAMP NOT NULL DEFAULT NOW(),
    resultados_encontrados INT       NOT NULL DEFAULT 0
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO catalogo_peliculas (titulo, genero, sinopsis, popularidad) VALUES
('El Origen', 'Ciencia ficción', 'Un thriller sobre sueños compartidos.', 8.7),
('La Gran Función', 'Drama', 'Una historia de amor y reinvención en el cine.', 7.4),
('Noche de Palomitas', 'Comedia', 'Un grupo de amigos enfrenta una noche inolvidable.', 6.9);

INSERT INTO etiquetas (nombre, categoria, descripcion, estado) VALUES
('Estreno', 'Promoción', 'Películas recién estrenadas', TRUE),
('Familiar', 'Audiencia', 'Contenido apto para toda la familia', TRUE),
('Acción', 'Género', 'Películas de acción rápida', TRUE);

INSERT INTO historial_busquedas (id_usuario, termino, resultados_encontrados) VALUES
(7, 'cine acción', 12),
(8, 'películas estreno', 5),
(9, 'comedia familiar', 8);
