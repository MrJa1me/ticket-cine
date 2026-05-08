\c promociones_db

DROP TABLE IF EXISTS aplicaciones_promo;
DROP TABLE IF EXISTS cupones;
DROP TABLE IF EXISTS campañas;
DROP TABLE IF EXISTS usuarios_proyeccion;

CREATE TABLE campañas (
    id_camp SERIAL PRIMARY KEY,
    nombre VARCHAR(50),
    fecha_fin DATE
);

CREATE TABLE cupones (
    codigo VARCHAR(20) PRIMARY KEY,
    id_camp INTEGER REFERENCES campañas(id_camp),
    pct_desc INTEGER,
    activo BOOLEAN
);

CREATE TABLE aplicaciones_promo (
    id_app SERIAL PRIMARY KEY,
    codigo_cupon VARCHAR(20) REFERENCES cupones(codigo),
    user_email VARCHAR(100),
    fecha_uso TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuarios_proyeccion (email VARCHAR(100) PRIMARY KEY, es_estudiante BOOLEAN);

INSERT INTO campañas (nombre, fecha_fin) VALUES ('Verano 26','2026-08-01'),('Navidad','2026-12-31'),('Estudiantes','2026-12-31'),('CyberDay','2026-06-01'),('Black Friday','2026-11-30'),('Aniversario','2026-04-15'),('Halloween','2026-10-31'),('Apertura','2026-03-01'),('Fidelidad','2026-12-31');
INSERT INTO cupones (codigo, id_camp, pct_desc, activo) SELECT 'CUPON-'||id_camp, id_camp, 10, true FROM campañas;
INSERT INTO aplicaciones_promo (codigo_cupon, user_email) SELECT codigo, 'u1@test.com' FROM cupones;
