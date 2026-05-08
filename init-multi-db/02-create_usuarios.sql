\c usuarios_db

DROP TABLE IF EXISTS membresias;
DROP TABLE IF EXISTS perfiles;
DROP TABLE IF EXISTS usuarios;

-- Maestro de Usuarios
CREATE TABLE usuarios (
    email VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    pais_codigo CHAR(2),
    fecha_registro DATE DEFAULT CURRENT_DATE
);

-- Detalle de perfil
CREATE TABLE perfiles (
    id_perfil SERIAL PRIMARY KEY,
    user_email VARCHAR(100) REFERENCES usuarios(email),
    preferencia_idioma VARCHAR(10),
    es_estudiante BOOLEAN DEFAULT FALSE,
    biografia VARCHAR(255)
);

-- Gestión de lealtad
CREATE TABLE membresias (
    id_membresia SERIAL PRIMARY KEY,
    user_email VARCHAR(100) REFERENCES usuarios(email),
    nivel VARCHAR(20), -- GOLD, SILVER, BRONZE
    puntos_acumulados INTEGER,
    fecha_vencimiento DATE
);

-- Datos de prueba (9 registros)
INSERT INTO usuarios VALUES 
('u1@test.com','User 1','55501','MX','2024-01-01'),('u2@test.com','User 2','55502','CL','2024-01-02'),
('u3@test.com','User 3','55503','AR','2024-01-03'),('u4@test.com','User 4','55504','CO','2024-01-04'),
('u5@test.com','User 5','55505','PE','2024-01-05'),('u6@test.com','User 6','55506','MX','2024-01-06'),
('u7@test.com','User 7','55507','ES','2024-01-07'),('u8@test.com','User 8','55508','US','2024-01-08'),
('u9@test.com','User 9','55509','CL','2024-01-09');

INSERT INTO perfiles (user_email, preferencia_idioma, es_estudiante) 
SELECT email, 'ES', (random() > 0.5) FROM usuarios;

INSERT INTO membresias (user_email, nivel, puntos_acumulados)
SELECT email, 'BRONZE', 100 FROM usuarios;