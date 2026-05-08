@echo off
setlocal

:MENU
cls
echo.
echo ============================================
echo   Ticketcine - MENU PRINCIPAL
echo ============================================
echo.
echo   [1] Iniciar todos los servicios (dev)
echo   [2] Iniciar todos los servicios (test)
echo   [3] Compilar microservicios
echo   [4] Reinstalar dependencias Maven
echo.
echo   --- Servicios individuales ---
echo   [5] Iniciar Eureka
echo   [6] Iniciar ms-autenticacion
echo   [7] Iniciar ms-usuarios
echo   [8] Iniciar ms-busqueda
echo   [9] Iniciar ms-horarios
echo   [10] Iniciar ms-salas
echo   [11] Iniciar ms-reserva
echo   [12] Iniciar ms-pagos
echo   [13] Iniciar ms-notificaciones
echo   [14] Iniciar ms-validacion
echo   [15] Iniciar ms-promociones
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
if "%opcion%"=="6" goto RUN_AUTENTICACION
if "%opcion%"=="7" goto RUN_USUARIOS
if "%opcion%"=="8" goto RUN_BUSQUEDA
if "%opcion%"=="9" goto RUN_HORARIOS
if "%opcion%"=="10" goto RUN_SALAS
if "%opcion%"=="11" goto RUN_RESERVA
if "%opcion%"=="12" goto RUN_PAGOS
if "%opcion%"=="13" goto RUN_NOTIFICACIONES
if "%opcion%"=="14" goto RUN_VALIDACION
if "%opcion%"=="15" goto RUN_PROMOCIONES
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
start "MS-AUTENTICACION" mvn -f ms-autenticacion spring-boot:run
start "MS-USUARIOS" mvn -f ms-usuarios spring-boot:run
start "MS-BUSQUEDA" mvn -f ms-busqueda spring-boot:run
start "MS-HORARIOS" mvn -f ms-horarios spring-boot:run
start "MS-SALAS" mvn -f ms-salas spring-boot:run
start "MS-RESERVA" mvn -f ms-reserva spring-boot:run
start "MS-PAGOS" mvn -f ms-pagos spring-boot:run
start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run
start "MS-VALIDACION" mvn -f ms-validacion spring-boot:run
start "MS-PROMOCIONES" mvn -f ms-promociones spring-boot:run
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
start "MS-AUTENTICACION" java -jar ms-autenticacion\\target\\cl-ticketcine-autenticacion-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-USUARIOS" java -jar ms-usuarios\\target\\cl-ticketcine-usuarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-BUSQUEDA" java -jar ms-busqueda\\target\\cl-ticketcine-busqueda-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-HORARIOS" java -jar ms-horarios\\target\\cl-ticketcine-horarios-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-SALAS" java -jar ms-salas\\target\\cl-ticketcine-salas-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-RESERVA" java -jar ms-reserva\\target\\cl-ticketcine-reserva-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-PAGOS" java -jar ms-pagos\\target\\cl-ticketcine-pagos-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-NOTIFICACIONES" java -jar ms-notificaciones\\target\\cl-ticketcine-notificaciones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-VALIDACION" java -jar ms-validacion\\target\\cl-ticketcine-validacion-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
start "MS-PROMOCIONES" java -jar ms-promociones\\target\\cl-ticketcine-promociones-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
echo Todos los servicios han sido lanzados en modo test.
pause
goto MENU

:COMPILE
cls
echo.
echo ===== Compilando microservicios =====
cd /d C:\ticket-cine1\ms-autenticacion
call mvn clean install -U
cd /d C:\ticket-cine1\ms-usuarios
call mvn clean install -U
cd /d C:\ticket-cine1\ms-busqueda
call mvn clean install -U
cd /d C:\ticket-cine1\ms-horarios
call mvn clean install -U
cd /d C:\ticket-cine1\ms-salas
call mvn clean install -U
cd /d C:\ticket-cine1\ms-reserva
call mvn clean install -U
cd /d C:\ticket-cine1\ms-pagos
call mvn clean install -U
cd /d C:\ticket-cine1\ms-notificaciones
call mvn clean install -U
cd /d C:\ticket-cine1\ms-validacion
call mvn clean install -U
cd /d C:\ticket-cine1\ms-promociones
call mvn clean install -U
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
rmdir /s /q C:\ticket-cine1\eureka\target
rmdir /s /q C:\ticket-cine1\ms-autenticacion\target
rmdir /s /q C:\ticket-cine1\ms-usuarios\target
rmdir /s /q C:\ticket-cine1\ms-busqueda\target
rmdir /s /q C:\ticket-cine1\ms-horarios\target
rmdir /s /q C:\ticket-cine1\ms-salas\target
rmdir /s /q C:\ticket-cine1\ms-reserva\target
rmdir /s /q C:\ticket-cine1\ms-pagos\target
rmdir /s /q C:\ticket-cine1\ms-notificaciones\target
rmdir /s /q C:\ticket-cine1\ms-validacion\target
rmdir /s /q C:\ticket-cine1\ms-promociones\target
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -DskipTests
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

:RUN_AUTENTICACION
cls
echo.
echo ===== Iniciando ms-autenticacion =====
start "MS-AUTENTICACION" mvn -f ms-autenticacion spring-boot:run
echo ms-autenticacion iniciado.
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

:RUN_BUSQUEDA
cls
echo.
echo ===== Iniciando ms-busqueda =====
start "MS-BUSQUEDA" mvn -f ms-busqueda spring-boot:run
echo ms-busqueda iniciado.
pause
goto MENU

:RUN_HORARIOS
cls
echo.
echo ===== Iniciando ms-horarios =====
start "MS-HORARIOS" mvn -f ms-horarios spring-boot:run
echo ms-horarios iniciado.
pause
goto MENU

:RUN_SALAS
cls
echo.
echo ===== Iniciando ms-salas =====
start "MS-SALAS" mvn -f ms-salas spring-boot:run
echo ms-salas iniciado.
pause
goto MENU

:RUN_RESERVA
cls
echo.
echo ===== Iniciando ms-reserva =====
start "MS-RESERVA" mvn -f ms-reserva spring-boot:run
echo ms-reserva iniciado.
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

:RUN_NOTIFICACIONES
cls
echo.
echo ===== Iniciando ms-notificaciones =====
start "MS-NOTIFICACIONES" mvn -f ms-notificaciones spring-boot:run
echo ms-notificaciones iniciado.
pause
goto MENU

:RUN_VALIDACION
cls
echo.
echo ===== Iniciando ms-validacion =====
start "MS-VALIDACION" mvn -f ms-validacion spring-boot:run
echo ms-validacion iniciado.
pause
goto MENU

:RUN_PROMOCIONES
cls
echo.
echo ===== Iniciando ms-promociones =====
start "MS-PROMOCIONES" mvn -f ms-promociones spring-boot:run
echo ms-promociones iniciado.
pause
goto MENU

:SALIR
cls
echo.
echo   Hasta luego.
echo.
endlocal
exit /b
