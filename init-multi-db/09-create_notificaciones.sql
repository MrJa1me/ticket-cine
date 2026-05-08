\c notificaciones_db

DROP TABLE IF EXISTS logs_error;
DROP TABLE IF EXISTS cola_envios;
DROP TABLE IF EXISTS plantillas;
DROP TABLE IF EXISTS usuarios_proyeccion;

CREATE TABLE plantillas (
    id_plantilla VARCHAR(20) PRIMARY KEY,
    asunto VARCHAR(100),
    contenido TEXT
);

CREATE TABLE cola_envios (
    id_notif SERIAL PRIMARY KEY,
    user_email VARCHAR(100),
    id_plantilla VARCHAR(20) REFERENCES plantillas(id_plantilla),
    estado_envio VARCHAR(10) -- SENT, FAIL
);

CREATE TABLE logs_error (
    id_log SERIAL PRIMARY KEY,
    id_notif INTEGER REFERENCES cola_envios(id_notif),
    error_msg VARCHAR(255)
);

CREATE TABLE usuarios_proyeccion (email VARCHAR(100) PRIMARY KEY, nombre VARCHAR(100));

INSERT INTO plantillas VALUES ('BIENVENIDA','Hola!','Bienvenido al cine'),('CONFIRMACION','Reserva OK','Tu ticket está listo'),('PROMO','Descuento','Aprovecha este 20%'),('RECORDATORIO','No olvides','Tu función empieza pronto'),('CUMPLE','Felicidades','Regalo para ti'),('AVISO_SALA','Cambio Sala','Atención a tu correo'),('PAGO_ERROR','Error Pago','Intenta de nuevo'),('CANCELADO','Reserva Cancelada','Confirmamos tu aviso'),('REEMBOLSO','Dinero Devuelto','Proceso completado');
INSERT INTO cola_envios (user_email, id_plantilla, estado_envio) VALUES ('u1@test.com','BIENVENIDA','SENT'),('u1@test.com','CONFIRMACION','SENT'),('u2@test.com','BIENVENIDA','SENT'),('u3@test.com','BIENVENIDA','SENT'),('u4@test.com','BIENVENIDA','SENT'),('u5@test.com','BIENVENIDA','SENT'),('u6@test.com','BIENVENIDA','SENT'),('u7@test.com','BIENVENIDA','SENT'),('u8@test.com','BIENVENIDA','SENT');
INSERT INTO logs_error (id_notif, error_msg) SELECT id_notif, 'No error' FROM cola_envios;