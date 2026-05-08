\c busqueda_db

DROP TABLE IF EXISTS elenco;
DROP TABLE IF EXISTS peliculas;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS usuarios_proyeccion;

CREATE TABLE categorias (
    id_cat SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE peliculas (
    slug VARCHAR(100) PRIMARY KEY,
    titulo VARCHAR(150),
    id_cat INTEGER REFERENCES categorias(id_cat),
    duracion_min INTEGER,
    estreno_anio INTEGER
);

CREATE TABLE elenco (
    id_actor SERIAL PRIMARY KEY,
    peli_slug VARCHAR(100) REFERENCES peliculas(slug),
    nombre_actor VARCHAR(100),
    papel VARCHAR(50)
);

-- Proyección para recomendaciones personalizadas
CREATE TABLE usuarios_proyeccion (
    email VARCHAR(100) PRIMARY KEY,
    es_estudiante BOOLEAN
);

-- Datos (9 registros por tabla)
INSERT INTO categorias (nombre) VALUES ('Accion'),('Drama'),('Terror'),('Sci-Fi'),('Comedia'),('Doc'),('Anime'),('Musical'),('Infantil');
INSERT INTO peliculas VALUES 
('p1','Matrix',4,136,1999),('p2','Titanic',2,194,1997),('p3','Alien',3,117,1979),
('p4','Inception',4,148,2010),('p5','Toy Story',9,81,1995),('p6','Gladiator',1,155,2000),
('p7','Coco',9,105,2017),('p8','Joker',2,122,2019),('p9','The Thing',3,109,1982);

INSERT INTO elenco (peli_slug, nombre_actor) 
SELECT slug, 'Actor Principal' FROM peliculas;

INSERT INTO usuarios_proyeccion VALUES ('u1@test.com', true), ('u2@test.com', false);