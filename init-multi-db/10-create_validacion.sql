\c validacion_db

DROP TABLE IF EXISTS historial_accesos;
DROP TABLE IF EXISTS puntos_control;
DROP TABLE IF EXISTS qr_codigos;
DROP TABLE IF EXISTS reservas_proyeccion;

CREATE TABLE qr_codigos (
    id_qr SERIAL PRIMARY KEY,
    reserva_id UUID,
    hash_code TEXT,
    activo BOOLEAN
);

CREATE TABLE puntos_control (
    id_punto VARCHAR(10) PRIMARY KEY,
    ubicacion VARCHAR(50)
);

CREATE TABLE historial_accesos (
    id_acc SERIAL PRIMARY KEY,
    id_qr INTEGER REFERENCES qr_codigos(id_qr),
    id_punto VARCHAR(10) REFERENCES puntos_control(id_punto),
    timestamp_ingreso TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reservas_proyeccion (id_reserva UUID PRIMARY KEY, user_email VARCHAR(100));

INSERT INTO puntos_control VALUES ('ENT-01','Entrada Principal'),('ENT-02','Entrada VIP'),('ENT-03','Piso 2'),('ENT-04','Sala IMAX'),('ENT-05','Snack Bar'),('ENT-06','Salida Sur'),('ENT-07','Parking'),('ENT-08','Admin'),('ENT-09','Soporte');
INSERT INTO qr_codigos (reserva_id, hash_code, activo) SELECT gen_random_uuid(), md5(random()::text), true FROM generate_series(1,9);
INSERT INTO historial_accesos (id_qr, id_punto) SELECT id_qr, 'ENT-01' FROM qr_codigos;