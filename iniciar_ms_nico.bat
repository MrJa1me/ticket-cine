@echo off
REM ============================================
REM  Iniciar MS de Nico - TicketCine
REM  Levanta Eureka + Common + 5 microservicios
REM ============================================
echo.
echo 🐧 Iniciando microservicios de Nico...
echo ==========================================
echo.

set BASE=C:\Trabajo-Duoc\ticket-cine

REM --- 1. Eureka (debe iniciar primero) ---
echo [1/6] Iniciando Eureka Server (puerto 8761)...
start "Eureka" cmd /c "cd /d %BASE%\eureka && mvn -q spring-boot:run"
echo Esperando 12 segundos para que Eureka levante...
timeout /t 12 /nobreak >nul

REM --- 2. ms-autenticacion ---
echo [2/6] Iniciando ms-autenticacion (puerto 9001)...
start "ms-autenticacion" cmd /c "cd /d %BASE%\ms-autenticacion && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 3. ms-usuarios ---
echo [3/6] Iniciando ms-usuarios (puerto 9002)...
start "ms-usuarios" cmd /c "cd /d %BASE%\ms-usuarios && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 4. ms-busqueda ---
echo [4/6] Iniciando ms-busqueda (puerto 9003)...
start "ms-busqueda" cmd /c "cd /d %BASE%\ms-busqueda && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 5. ms-salas ---
echo [5/6] Iniciando ms-salas (puerto 9005)...
start "ms-salas" cmd /c "cd /d %BASE%\ms-salas && mvn -q spring-boot:run"
timeout /t 3 /nobreak >nul

REM --- 6. ms-validacion ---
echo [6/6] Iniciando ms-validacion (puerto 9009)...
start "ms-validacion" cmd /c "cd /d %BASE%\ms-validacion && mvn -q spring-boot:run"

echo.
echo ==========================================
echo  Todos los MS iniciados 🐧
echo.
echo  Puertos:
echo    Eureka:          http://localhost:8761
echo    ms-autenticacion: http://localhost:9001
echo    ms-usuarios:      http://localhost:9002
echo    ms-busqueda:      http://localhost:9003
echo    ms-salas:         http://localhost:9005
echo    ms-validacion:    http://localhost:9009
echo ==========================================
echo.
pause
