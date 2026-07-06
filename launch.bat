@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo   TicketCine - MENU PRINCIPAL
echo ============================================
echo.
echo   [1] Iniciar todos los servicios (dev)
echo   [2] Iniciar todos los servicios (test)
echo   [3] Compilar microservicios
echo   [4] Reinstalar dependencias Maven
echo.
echo   --- Servicios individuales ---
echo   [5] Iniciar Eureka
echo   [6] Iniciar ms-boletos
echo   [7] Iniciar ms-catalogo
echo   [8] Iniciar ms-compras
echo   [9] Iniciar ms-pagos
echo   [10] Iniciar ms-usuarios
echo   [11] Iniciar API Gateway
echo.
echo   [0] Salir
echo.
echo ============================================
set /p opcion="  Selecciona una opcion: "

if "%opcion%"=="1" goto RUN_ALL
if "%opcion%"=="2" goto RUN_TEST
if "%opcion%"=="3" goto COMPILE
if "%opcion%"=="4" goto INSTALL
if "%opcion%"=="5" goto RUN_EUREKA
if "%opcion%"=="6" goto RUN_BOLETOS
if "%opcion%"=="7" goto RUN_CATALOGO
if "%opcion%"=="8" goto RUN_COMPRAS
if "%opcion%"=="9" goto RUN_PAGOS
if "%opcion%"=="10" goto RUN_USUARIOS
if "%opcion%"=="11" goto RUN_GATEWAY
if "%opcion%"=="0" goto SALIR

echo.
echo   Opcion invalida. Intenta de nuevo.
timeout /t 2 /nobreak > nul
goto MENU

REM ============================================

:RUN_ALL
cls
echo.
echo ===== Iniciando Eureka Server =====
start "EUREKA" mvn -f eureka spring-boot:run
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios =====

start "MS-BOLETOS" mvn -f ms-boletos spring-boot:run

start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run

start "MS-COMPRAS" mvn -f ms-compras spring-boot:run

start "MS-PAGOS" mvn -f ms-pagos spring-boot:run

start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run
rem  Se inicia el API Gateway despues de los microservicios para que encuentre servicios en Eureka
timeout /t 5 /nobreak > nul
echo ===== Iniciando API Gateway =====
start "API-GATEWAY" mvn -f api-gateway spring-boot:run

echo Todos los servicios han sido lanzados.
pause
goto MENU

:RUN_TEST
cls
echo.
echo ===== Iniciando Eureka Server (test) =====
start "EUREKA" java -jar eureka\target\cl-ticketcine-eureka-1.0-SNAPSHOT.jar --spring.profiles.active=test
timeout /t 5 /nobreak > nul
echo ===== Iniciando Microservicios (test) =====

start "MS-BOLETOS" java -jar ms-boletos\target\cl-ticketcine-boletos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-CATALOGO" java -jar ms-catalogo\target\cl-ticketcine-catalogo-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-COMPRAS" java -jar ms-compras\target\cl-ticketcine-compras-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-PAGOS" java -jar ms-pagos\target\cl-ticketcine-pagos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

start "MS-USUARIOS" java -jar ms-usuarios\target\cl-ticketcine-usuarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
rem  Se inicia el API Gateway en modo test
timeout /t 5 /nobreak > nul
echo ===== Iniciando API Gateway (test) =====
start "API-GATEWAY" java -jar api-gateway\target\cl-ticketcine-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=test

echo Todos los servicios han sido lanzados en modo test.
pause
goto MENU

:COMPILE
cls
echo.
echo ===== Compilando microservicios =====
cd /d C:\TicketCine\common
call mvn clean install -U

cd /d C:\TicketCine\ms-boletos
call mvn clean install -U

cd /d C:\TicketCine\ms-catalogo
call mvn clean install -U

cd /d C:\TicketCine\ms-compras
call mvn clean install -U

cd /d C:\TicketCine\ms-pagos
call mvn clean install -U

cd /d C:\TicketCine\ms-usuarios
call mvn clean install -U

cd /d C:\TicketCine\api-gateway
call mvn clean install -U

cd /d C:\TicketCine
echo Compilacion completada.
pause
goto MENU

:INSTALL
cls
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2
echo Eliminando carpetas target ...
rmdir /s /q C:\TicketCine\eureka\target
rmdir /s /q C:\TicketCine\ms-boletos\target
rmdir /s /q C:\TicketCine\ms-catalogo\target
rmdir /s /q C:\TicketCine\ms-compras\target
rmdir /s /q C:\TicketCine\ms-pagos\target
rmdir /s /q C:\TicketCine\ms-usuarios\target
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -Dmaven.test.skip=true
echo.
echo === PROCESO COMPLETADO ===
pause
goto MENU

:RUN_EUREKA
cls
echo.
echo ===== Iniciando Eureka =====
start "EUREKA" mvn -f eureka spring-boot:run
echo Eureka iniciado.
pause
goto MENU

:RUN_BOLETOS
cls
echo.
echo ===== Iniciando ms-boletos =====
start "MS-BOLETOS" mvn -f ms-boletos spring-boot:run
echo ms-boletos iniciado.
pause
goto MENU

:RUN_CATALOGO
cls
echo.
echo ===== Iniciando ms-catalogo =====
start "MS-CATALOGO" mvn -f ms-catalogo spring-boot:run
echo ms-catalogo iniciado.
pause
goto MENU

:RUN_COMPRAS
cls
echo.
echo ===== Iniciando ms-compras =====
start "MS-COMPRAS" mvn -f ms-compras spring-boot:run
echo ms-compras iniciado.
pause
goto MENU

:RUN_PAGOS
cls
echo.
echo ===== Iniciando ms-pagos =====
start "MS-PAGOS" mvn -f ms-pagos spring-boot:run
echo ms-pagos iniciado.
pause
goto MENU

:RUN_USUARIOS
cls
echo.
echo ===== Iniciando ms-usuarios =====
start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run
echo ms-usuarios iniciado.
pause
goto MENU

:RUN_GATEWAY
cls
echo.
echo ===== Iniciando API Gateway =====
start "API-GATEWAY" mvn -f api-gateway spring-boot:run
echo API Gateway iniciado en puerto 9000.
pause
goto MENU

:SALIR
cls
echo.
echo   Hasta luego :D.
echo.
endlocal
exit /b
