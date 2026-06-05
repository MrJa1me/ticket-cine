@echo off
REM ============================================
REM  Iniciar MS de Nico - TicketCine
REM  Levanta Eureka + Common + 10 microservicios
REM ============================================
echo.
echo 🐧 Iniciando microservicios de Nico...
echo ==========================================
echo.

set BASE=C:/ticket-cine

REM --- 1. Eureka (debe iniciar primero) ---
echo [1/11] Iniciando Eureka Server (puerto 8761)...
start "Eureka" cmd /c "cd /d %BASE%\eureka && mvn -q spring-boot:run"
echo Esperando 12 segundos para que Eureka levante...
timeout /t 12 /nobreak >nul

REM --- 2. ms-autenticacion ---
echo [2/11] Iniciando ms-autenticacion (puerto 9001)...
start "ms-autenticacion" cmd /c "cd /d %BASE%\ms-autenticacion && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 3. ms-usuarios ---
echo [3/11] Iniciando ms-usuarios (puerto 9002)...
start "ms-usuarios" cmd /c "cd /d %BASE%\ms-usuarios && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 4. ms-busqueda ---
echo [4/11] Iniciando ms-busqueda (puerto 9003)...
start "ms-busqueda" cmd /c "cd /d %BASE%\ms-busqueda && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 5. ms-salas ---
echo [5/11] Iniciando ms-salas (puerto 9005)...
start "ms-salas" cmd /c "cd /d %BASE%\ms-salas && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 6. ms-horarios ---
echo [6/11] Iniciando ms-horarios (puerto 9004)...
start "ms-horarios" cmd /c "cd /d %BASE%\ms-horarios && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 7. ms-reserva ---
echo [7/10] Iniciando ms-reserva (puerto 9006)...
start "ms-reserva" cmd /c "cd /d %BASE%\ms-reserva && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 8. ms-pagos ---
echo [8/10] Iniciando ms-pagos (puerto 9007)...
start "ms-pagos" cmd /c "cd /d %BASE%\ms-pagos && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 9. ms-notificaciones ---
echo [9/11] Iniciando ms-notificaciones (puerto 9008)...
start "ms-notificaciones" cmd /c "cd /d %BASE%\ms-notificaciones && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 10. ms-validacion ---
echo [10/11] Iniciando ms-validacion (puerto 9009)...
start "ms-validacion" cmd /c "cd /d %BASE%\ms-validacion && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 11. ms-promociones ---
echo [11/11] Iniciando ms-promociones (puerto 9010)...
start "ms-promociones" cmd /c "cd /d %BASE%\ms-promociones && mvn -q spring-boot:run"

echo.
echo ==========================================
echo  Todos los MS iniciados 🐧
echo.
echo  Puertos:
echo    Eureka:          http://localhost:8761
echo    ms-autenticacion: http://localhost:9001
echo    ms-usuarios:      http://localhost:9002
echo    ms-busqueda:      http://localhost:9003
echo    ms-horarios:      http://localhost:9004
echo    ms-salas:         http://localhost:9005
echo    ms-reserva:       http://localhost:9006
echo    ms-pagos:         http://localhost:9007
echo    ms-notificaciones: http://localhost:9008
echo    ms-validacion:    http://localhost:9009
echo    ms-promociones:   http://localhost:9010
echo ==========================================
echo.
pause
