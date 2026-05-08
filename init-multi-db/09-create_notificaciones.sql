-- CADA BASE DE DATOS DE CADA MICROSERVICIO DEBE TENER SU PROPIO
-- SCRIPT DE CREACIÓN DE TABLAS E INSERCIÓN DE DATOS

-- Conectarse a la base de datos específica para este microservicio
\c notificaciones;

-- 1. ELIMINACIÓN (Orden jerárquico inverso)
DROP TABLE IF EXISTS suscripciones_push;
DROP TABLE IF EXISTS plantillas;
DROP TABLE IF EXISTS correos_enviados;

-- 2. TABLAS MAESTRAS
CREATE TABLE correos_enviados (
    id_correo         SERIAL       PRIMARY KEY,
    destinatario      VARCHAR(100) NOT NULL,
    asunto            VARCHAR(150) NOT NULL,
    cuerpo            TEXT         NOT NULL,
    fecha_envio       TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE plantillas (
    id_plantilla      SERIAL       PRIMARY KEY,
    nombre_evento     VARCHAR(50)  NOT NULL,
    contenido_html    TEXT         NOT NULL,
    version           INT          NOT NULL,
    lenguaje          VARCHAR(5)   NOT NULL
);

CREATE TABLE suscripciones_push (
    id_suscrip        SERIAL       PRIMARY KEY,
    id_usuario        INT          NOT NULL,
    endpoint          TEXT         NOT NULL,
    auth_key          VARCHAR(100) NOT NULL,
    p256dh            VARCHAR(100) NOT NULL
);

-- 3. INSERCIÓN DE DATOS
INSERT INTO correos_enviados (destinatario, asunto, cuerpo) VALUES
('cliente@cine.cl', 'Reserva confirmada', 'Tu reserva ha sido confirmada. ¡Gracias por elegirnos!');

INSERT INTO plantillas (nombre_evento, contenido_html, version, lenguaje) VALUES
('reserva_confirmada', '<p>Tu reserva fue confirmada.</p>', 1, 'es');

INSERT INTO suscripciones_push (id_usuario, endpoint, auth_key, p256dh) VALUES
(7, 'https://push.example.com/endpoint/123', 'authkey-example', 'p256dh-example');
