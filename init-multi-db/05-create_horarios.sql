\c horarios_db

DROP TABLE IF EXISTS precios_horario;
DROP TABLE IF EXISTS turnos;
DROP TABLE IF EXISTS funciones;
DROP TABLE IF EXISTS peliculas_proyeccion;

CREATE TABLE funciones (
    id_fun SERIAL PRIMARY KEY,
    peli_slug VARCHAR(100),
    sala_id VARCHAR(10),
    fecha_dia DATE
);

CREATE TABLE turnos (
    id_turno SERIAL PRIMARY KEY,
    id_fun INTEGER REFERENCES funciones(id_fun),
    hora_inicio TIME,
    es_prime BOOLEAN
);

CREATE TABLE precios_horario (
    id_precio SERIAL PRIMARY KEY,
    id_turno INTEGER REFERENCES turnos(id_turno),
    monto_base DECIMAL(10,2),
    moneda VARCHAR(3)
);

-- Proyección necesaria para validar que la película existe
CREATE TABLE peliculas_proyeccion (
    slug VARCHAR(100) PRIMARY KEY,
    titulo VARCHAR(150)
);

INSERT INTO peliculas_proyeccion VALUES ('p1','Matrix'),('p2','Titanic'),('p3','Alien'),('p4','Inception'),('p5','Toy Story'),('p6','Gladiator'),('p7','Coco'),('p8','Joker'),('p9','The Thing');

INSERT INTO funciones (peli_slug, sala_id, fecha_dia) 
SELECT slug, 'SALA-1', '2026-05-10' FROM peliculas_proyeccion;

INSERT INTO turnos (id_fun, hora_inicio, es_prime) 
SELECT id_fun, '20:00:00', true FROM funciones;

INSERT INTO precios_horario (id_turno, monto_base, moneda)
SELECT id_turno, 5000.00, 'CLP' FROM turnos;