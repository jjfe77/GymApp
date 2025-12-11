@echo off
echo ========================================
echo   Git Add + Commit + Push a main
echo ========================================
echo.

set /p msg="Ingrese el mensaje del commit: "

echo.
echo Ejecutando git add .
git add .

echo.
echo Realizando commit...
git commit -m "%msg%"

echo.
echo Haciendo push a origin/main...
git push origin main

echo.
echo Operacion completada!
pause
