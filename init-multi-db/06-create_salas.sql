\c salas_db

DROP TABLE IF EXISTS mantenimiento;
DROP TABLE IF EXISTS asientos;
DROP TABLE IF EXISTS salas;

CREATE TABLE salas (
    id_sala VARCHAR(10) PRIMARY KEY,
    formato VARCHAR(10), -- 2D, 3D, IMAX
    capacidad INTEGER
);

CREATE TABLE asientos (
    id_asiento SERIAL PRIMARY KEY,
    id_sala VARCHAR(10) REFERENCES salas(id_sala),
    fila CHAR(1),
    numero INTEGER
);

CREATE TABLE mantenimiento (
    id_maint SERIAL PRIMARY KEY,
    id_sala VARCHAR(10) REFERENCES salas(id_sala),
    ultima_fecha DATE,
    tecnico_nombre VARCHAR(100)
);

INSERT INTO salas VALUES ('S1','IMAX',100),('S2','2D',150),('S3','3D',120),('S4','IMAX',100),('S5','2D',150),('S6','3D',120),('S7','2D',80),('S8','2D',80),('S9','VIP',40);
INSERT INTO asientos (id_sala, fila, numero) SELECT id_sala, 'A', 1 FROM salas;
INSERT INTO mantenimiento (id_sala, ultima_fecha) SELECT id_sala, '2026-01-01' FROM salas;