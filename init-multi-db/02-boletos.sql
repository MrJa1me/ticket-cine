-- =========================================
-- 02-boletos.sql
-- =========================================

-- Conectarse a la base de datos específica
 \c boletos;

-- 1. ELIMINACIÓN (Orden jerárquico inverso para evitar errores de FK)
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS boletos;
DROP TABLE IF EXISTS zonas;
DROP TABLE IF EXISTS proy_Peliculas;

-- 2. CREACIÓN DE TABLAS DE PROYECCIÓN (Datos externos necesarios localmente)
CREATE TABLE proy_Peliculas (
    id_Pelicula INTEGER PRIMARY KEY,
    nombre_Pelicula VARCHAR(100),
    fecha DATE
);

-- 3. CREACIÓN DE TABLAS MAESTRAS DEL MICROSERVICIO
CREATE TABLE zonas (
    id_zona SERIAL PRIMARY KEY,
    nombre VARCHAR(50), -- VIP, Cancha General, Platea Alta
    capacidad INT,
    precio_base NUMERIC(10,2),
    estado VARCHAR(20) -- Activa, Agotada, En Mantenimiento
);

CREATE TABLE boletos (
    id_boleto SERIAL PRIMARY KEY,
    id_Pelicula INT REFERENCES proy_Peliculas(id_Pelicula), --agregado
    id_zona INT REFERENCES zonas(id_zona),            --agregado
    codigo VARCHAR(50),
    tipo VARCHAR(50), -- Digital, Físico, VIP-Pass
    estado VARCHAR(20), -- Disponible, Vendido, Reservado, Anulado
    fecha_emision DATE
);

CREATE TABLE reservas (
    id_reserva SERIAL PRIMARY KEY,
    id_boleto INT REFERENCES boletos(id_boleto),
    fecha_reserva DATE,
    estado VARCHAR(20), -- Pendiente, Confirmada, Expirada
    expiracion DATE
);

-- 4. INSERCIÓN DE DATOS (Poblamiento)

-- Poblamiento de Proyecciones (Basado en Peliculas conocidos)
INSERT INTO proy_Peliculas (id_Pelicula, nombre_Pelicula, fecha) VALUES
(1, 'Dune: Parte Dos', '2025-12-20'),
(2, 'Oppenheimer', '2025-11-15'),
(3, 'Intensamente 2', '2025-10-01'),
(4, 'Deadpool & Wolverine', '2026-03-15');

-- Poblamiento de Zonas
INSERT INTO zonas (nombre, capacidad, precio_base, estado) VALUES
('VIP Experience', 200, 150000.00, 'Activa'),
('Cancha General', 5000, 45000.00, 'Activa'),
('Platea Alta Orientada', 1500, 85000.00, 'Activa'),
('Galería', 3000, 25000.00, 'Agotada');

-- Poblamiento de Boletos (id_Pelicula → proy_Peliculas / catalogo.Peliculas | id_zona → zonas)
INSERT INTO boletos (id_Pelicula, id_zona, codigo, tipo, estado, fecha_emision) VALUES
(1, 2, 'TKT-LB-001', 'Digital', 'Vendido', '2025-05-01'),    -- ID 1: Los Bunkers, Cancha
(1, 2, 'TKT-LB-002', 'Digital', 'Vendido', '2025-05-01'),    -- ID 2: Los Bunkers, Cancha
(2, 2, 'TKT-DL-501', 'Físico', 'Reservado', '2025-05-10'),   -- ID 3: Dua Lipa, Cancha
(2, 1, 'TKT-DL-502', 'Digital', 'Vendido', '2025-05-10'),    -- ID 4: Dua Lipa, VIP
(2, 1, 'TKT-DL-999', 'Digital', 'Anulado', '2025-05-12');    -- ID 5: Dua Lipa, VIP (anulado)

-- Poblamiento de Reservas
INSERT INTO reservas (id_boleto, fecha_reserva, estado, expiracion) VALUES
(3, '2025-05-18', 'Pendiente', '2025-05-20'),
(4, '2025-05-15', 'Confirmada', '2025-05-15');