@echo off
echo ===================================
echo Starting Student Form Backend
echo ===================================
echo.
echo Your Computer IP: 192.168.0.4
echo Backend will run on: http://192.168.0.4:8080
echo.
echo Checking MySQL...
net start MySQL80 2>nul
if errorlevel 1 (
    net start MySQL 2>nul
    if errorlevel 1 (
        echo WARNING: Could not start MySQL automatically!
        echo Please start MySQL manually from Services.
        echo Press any key to continue anyway...
        pause >nul
    ) else (
        echo MySQL started successfully!
    )
) else (
    echo MySQL started successfully!
)

echo.
echo Starting backend server...
echo Please wait, downloading dependencies first time...
echo.

cd backend
..\mvnw.cmd spring-boot:run

pause
