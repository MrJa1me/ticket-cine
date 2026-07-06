@echo off
echo.
echo === REINSTALACION DE DEPENDENCIAS MAVEN ===
echo.

REM Paso 1: Eliminar carpeta local de dependencias
echo Eliminando carpeta .m2 ...
rmdir /s /q %USERPROFILE%\.m2

REM Paso 2: Eliminar carpetas target de los proyectos
echo Eliminando carpetas target ...
rmdir /s /q C:\TicketCine\common\target
rmdir /s /q C:\TicketCine\eureka\target
rmdir /s /q C:\TicketCine\api-gateway\target
rmdir /s /q C:\TicketCine\ms-boletos\target
rmdir /s /q C:\TicketCine\ms-catalogo\target
rmdir /s /q C:\TicketCine\ms-compras\target
rmdir /s /q C:\TicketCine\ms-pagos\target
rmdir /s /q C:\TicketCine\ms-usuarios\target


REM Paso 3: Instalar todas las dependencias forzadamente
echo Descargando dependencias nuevamente con Maven ...
mvn clean install -U -Dmaven.test.skip=true

echo.
echo === PROCESO COMPLETADO ===
pause
