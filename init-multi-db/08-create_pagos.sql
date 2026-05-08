\c pagos_db

DROP TABLE IF EXISTS reembolsos;
DROP TABLE IF EXISTS transacciones;
DROP TABLE IF EXISTS metodos_pago;
DROP TABLE IF EXISTS reservas_proyeccion;

CREATE TABLE metodos_pago (
    id_metodo VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(50)
);

CREATE TABLE transacciones (
    id_tx SERIAL PRIMARY KEY,
    reserva_id UUID,
    metodo_id VARCHAR(10) REFERENCES metodos_pago(id_metodo),
    monto DECIMAL(10,2),
    fecha_tx TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reembolsos (
    id_reem SERIAL PRIMARY KEY,
    id_tx INTEGER REFERENCES transacciones(id_tx),
    motivo VARCHAR(100),
    monto_devuelto DECIMAL(10,2)
);

CREATE TABLE reservas_proyeccion (id_reserva UUID PRIMARY KEY, monto_total DECIMAL(10,2));

INSERT INTO metodos_pago VALUES ('VISA','Visa'),('MSTC','Mastercard'),('AMEX','Amex'),('CASH','Efectivo'),('PAYP','PayPal'),('APPL','Apple Pay'),('GOOG','Google Pay'),('TRANS','Transferencia'),('BITC','Bitcoin');
INSERT INTO transacciones (reserva_id, metodo_id, monto) VALUES (gen_random_uuid(), 'VISA', 5000.00), (gen_random_uuid(), 'MSTC', 5000.00), (gen_random_uuid(), 'VISA', 5000.00), (gen_random_uuid(), 'VISA', 5000.00), (gen_random_uuid(), 'PAYP', 5000.00), (gen_random_uuid(), 'VISA', 5000.00), (gen_random_uuid(), 'CASH', 5000.00), (gen_random_uuid(), 'AMEX', 5000.00), (gen_random_uuid(), 'VISA', 5000.00);
INSERT INTO reembolsos (id_tx, motivo, monto_devuelto) SELECT id_tx, 'Cliente cancela', 5000.00 FROM transacciones LIMIT 9;