-- ES FUNDAMENTAL EJECUTAR ESTE SCRIPT QUE PERMITE ELIMINAR LAS BASES DE DATOS
-- SI ES QUE EXISTEN, PARA LUEGO CREARLAS LIMPIAS SIN TABLAS Y DESDE CERO

SELECT 'CREATE DATABASE usuarios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios') \gexec---borrar esta linea

SELECT 'CREATE DATABASE autenticacion'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'autenticacion') \gexec

SELECT 'CREATE DATABASE busqueda'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'busqueda') \gexec

SELECT 'CREATE DATABASE horarios'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'horarios') \gexec

SELECT 'CREATE DATABASE salas'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'salas') \gexec

SELECT 'CREATE DATABASE reserva'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'reserva') \gexec

SELECT 'CREATE DATABASE pagos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pagos') \gexec

SELECT 'CREATE DATABASE notificaciones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notificaciones') \gexec

SELECT 'CREATE DATABASE validacion'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'validacion') \gexec

SELECT 'CREATE DATABASE promociones'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'promociones') \gexec