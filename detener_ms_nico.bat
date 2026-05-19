@echo off
REM ============================================
REM  Detener MS de Nico - TicketCine
REM ============================================
echo.
echo 🐧 Deteniendo microservicios...
echo ==========================================
echo.

echo Cerrando Eureka...
taskkill /fi "WINDOWTITLE eq Eureka*" /f 2>nul

echo Cerrando ms-autenticacion...
taskkill /fi "WINDOWTITLE eq ms-autenticacion*" /f 2>nul

echo Cerrando ms-usuarios...
taskkill /fi "WINDOWTITLE eq ms-usuarios*" /f 2>nul

echo Cerrando ms-busqueda...
taskkill /fi "WINDOWTITLE eq ms-busqueda*" /f 2>nul

echo Cerrando ms-salas...
taskkill /fi "WINDOWTITLE eq ms-salas*" /f 2>nul

echo Cerrando ms-validacion...
taskkill /fi "WINDOWTITLE eq ms-validacion*" /f 2>nul

echo.
echo ==========================================
echo  Todos los MS detenidos ✅
echo ==========================================
pause
