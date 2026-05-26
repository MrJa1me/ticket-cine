@echo off
REM ============================================
REM  Compilar solo los MS de Nico - TicketCine
REM ============================================
echo.
echo 🐧 Compilando MS de Nico...
echo ==========================================
echo.

cd /d C:\Trabajo-Duoc\ticket-cine

echo [1/1] Compilando common + eureka + 10 MS
call mvn compile -pl common,eureka,ms-promociones,ms-autenticacion,ms-usuarios,ms-reserva,ms-salas,ms-pagos,ms-notificaciones,ms-busqueda,ms-validacion,ms-horarios -am

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ==========================================
    echo  COMPILACION EXITOSA ✅
    echo ==========================================
) else (
    echo.
    echo ==========================================
    echo  ERROR DE COMPILACION ❌
    echo  Revisa los errores arriba.
    echo ==========================================
)

pause
