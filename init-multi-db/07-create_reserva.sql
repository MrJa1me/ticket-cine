\c reserva_db

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS estados_reserva;
DROP TABLE IF EXISTS usuarios_proyeccion;
DROP TABLE IF EXISTS funciones_proyeccion;

CREATE TABLE estados_reserva (
    cod_estado VARCHAR(10) PRIMARY KEY,
    descripcion VARCHAR(50)
);

CREATE TABLE reservas (
    id_reserva UUID PRIMARY KEY,
    user_email VARCHAR(100),
    id_fun INTEGER,
    cod_estado VARCHAR(10) REFERENCES estados_reserva(cod_estado),
    creado_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tickets (
    id_ticket SERIAL PRIMARY KEY,
    id_reserva UUID REFERENCES reservas(id_reserva),
    asiento_id VARCHAR(10),
    precio_final DECIMAL(10,2)
);

-- Proyecciones
CREATE TABLE usuarios_proyeccion (email VARCHAR(100) PRIMARY KEY, nombre VARCHAR(100));
CREATE TABLE funciones_proyeccion (id_fun INTEGER PRIMARY KEY, resumen VARCHAR(150));

INSERT INTO estados_reserva VALUES ('PEND','Pendiente'),('CONF','Confirmada'),('CANC','Cancelada'),('EXPI','Expirada'),('PROC','En Proceso'),('PAGD','Pagada'),('REEM','Reembolsada'),('USAD','Usada'),('ERRO','Error');
-- Llenado de 9 reservas con UUIDs fijos para prueba
INSERT INTO reservas (id_reserva, user_email, id_fun, cod_estado) VALUES 
(gen_random_uuid(), 'u1@test.com', 1, 'CONF'), (gen_random_uuid(), 'u2@test.com', 2, 'CONF'),
(gen_random_uuid(), 'u3@test.com', 3, 'PEND'), (gen_random_uuid(), 'u4@test.com', 4, 'CANC'),
(gen_random_uuid(), 'u5@test.com', 5, 'CONF'), (gen_random_uuid(), 'u6@test.com', 6, 'CONF'),
(gen_random_uuid(), 'u7@test.com', 7, 'PEND'), (gen_random_uuid(), 'u8@test.com', 8, 'CONF'),
(gen_random_uuid(), 'u9@test.com', 9, 'CONF');
INSERT INTO tickets (id_reserva, asiento_id, precio_final) SELECT id_reserva, 'A1', 4500.00 FROM reservas;