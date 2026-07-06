@echo off
setlocal
cd /d "%~dp0"
cls
echo ====================================================
echo    TicketCine - DESPLIEGUE EN DOCKER
echo ====================================================
echo.

rem Paso 1: Detener y eliminar contenedores existentes
echo [1/5] DETENIENDO Y ELIMINANDO CONTENEDORES ANTERIORES...
docker compose down -v --remove-orphans 2>nul
echo       Contenedores y volumenes anteriores eliminados.
echo.

rem Paso 2: Compilar todos los JARs con Maven
echo [2/5] COMPILANDO PROYECTO JAVA CON MAVEN...
echo       (common, eureka, api-gateway, ms-boletos, ms-catalogo, ms-compras, ms-pagos, ms-usuarios)
echo.
call mvn clean package -Dmaven.test.skip=true -q
echo.
echo       Compilacion terminada.
echo.

rem COMPILAR jars adicionales sin dependencias (eureka, gateway)
echo [3/5] COMPILANDO JARS FINALES...
call mvn package -Dmaven.test.skip=true -q
echo.
echo       JARs generados en las carpetas target de cada MS.
echo.

rem Paso 4: Construir imagenes Docker y levantar todos los contenedores
echo [4/5] LEVANTANDO CONTENEDORES CON DOCKER COMPOSE...
echo.
docker compose up -d --build
echo.

rem Paso 5: Verificar estado de los contenedores
echo [5/5] VERIFICANDO ESTADO DE CONTENEDORES...
echo.
docker compose ps
echo.

echo ====================================================
echo   TicketCine desplegado exitosamente
echo ====================================================
echo.
echo   API Gateway:    http://localhost:9000
echo   Eureka Server:  http://localhost:8761
echo   Kafka UI:       http://localhost:8080
echo   PostgreSQL:     localhost:5433
echo.
echo   Microservicios disponibles:
echo     - ms-catalogo   (Peliculas y funciones)
echo     - ms-boletos    (Boletos y asientos)
echo     - ms-compras    (Compras)
echo     - ms-pagos      (Pagos)
echo     - ms-usuarios   (Usuarios y auth)
echo.
echo   Swagger UI (acceso directo al MS, no via gateway):
echo     - ms-usuarios:  http://localhost:9010/swagger-ui.html
echo     - ms-catalogo:  http://localhost:9003/swagger-ui.html
echo     - ms-boletos:   http://localhost:9002/swagger-ui.html
echo     - ms-compras:   http://localhost:9004/swagger-ui.html
echo     - ms-pagos:     http://localhost:9006/swagger-ui.html
echo.
echo   Credenciales de prueba (login en Swagger ms-usuarios):
echo     Email: ana@administrador.cl
echo     Pass:  TicketCine@2026
echo     (tambien: admin@ticketcine.cl con la misma clave)
echo.
echo   Guia de presentacion (Postman, Swagger, Gateway...):
echo     docs\GUIA-PRESENTACION.md
echo.
pause
