@echo off
title Student Form Backend Server
color 0A
echo.
echo ================================================
echo   STUDENT FORM BACKEND SERVER
echo ================================================
echo.
echo Computer IP: 192.168.0.4
echo Backend URL: http://192.168.0.4:8081
echo Database: MySQL (Required!)
echo.
echo Checking MySQL status...
echo.

REM Check if MySQL is running
sc query MySQL80 | find "RUNNING" >nul
if errorlevel 1 (
    echo [WARNING] MySQL is NOT running!
    echo.
    echo Please start MySQL first:
    echo   1. Open XAMPP Control Panel, OR
    echo   2. Run: net start MySQL80
    echo.
    echo See INSTALL_MYSQL.md for installation guide.
    echo.
    echo Press any key to try starting backend anyway...
    pause >nul
    echo.
) else (
    echo [OK] MySQL is running!
    echo.
)

echo Starting backend server...
echo Please keep this window open!
echo.
echo ================================================
echo.

cd backend
..\mvnw.cmd spring-boot:run

pause
