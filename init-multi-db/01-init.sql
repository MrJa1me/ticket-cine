-- Inicialización de las 10 bases de datos del ecosistema SaaS
SELECT 'CREATE DATABASE auth_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'auth_db') \gexec
SELECT 'CREATE DATABASE busqueda_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'busqueda_db') \gexec
SELECT 'CREATE DATABASE horarios_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'horarios_db') \gexec
SELECT 'CREATE DATABASE salas_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'salas_db') \gexec
SELECT 'CREATE DATABASE reserva_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'reserva_db') \gexec
SELECT 'CREATE DATABASE pagos_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'pagos_db') \gexec
SELECT 'CREATE DATABASE notificaciones_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notificaciones_db') \gexec
SELECT 'CREATE DATABASE usuarios_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'usuarios_db') \gexec
SELECT 'CREATE DATABASE validacion_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'validacion_db') \gexec
SELECT 'CREATE DATABASE promociones_db' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'promociones_db') \gexec