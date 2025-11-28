@echo off
title Student Form Backend Server
color 0A

echo.
echo ========================================
echo   Student Form Backend Server
echo ========================================
echo.
echo Computer IP: 192.168.0.4
echo Backend will run on: http://192.168.0.4:8081
echo.

echo Checking MySQL...
sc query mysqld >nul 2>&1
if %errorlevel% neq 0 (
    echo WARNING: MySQL service not found or not running!
    echo Please start MySQL in XAMPP first.
    echo.
    pause
)

echo Starting backend server...
echo Please wait, this may take 1-2 minutes...
echo.

REM Change to backend directory first!
cd backend

REM Run Spring Boot from backend directory
..\mvnw.cmd spring-boot:run

pause
