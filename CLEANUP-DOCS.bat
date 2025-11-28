@echo off
title Cleanup Documentation Files
color 0E

echo.
echo ========================================
echo   Cleanup Documentation Files
echo ========================================
echo.
echo This will DELETE:
echo - BUILD-AAB.bat
echo - database_schema.sql
echo - StudentFormAPI.postman_collection.json
echo.
echo This will KEEP:
echo - All source code (Java, XML)
echo - README.md
echo - .aab file
echo - Backend code
echo - Build configuration files
echo.

pause

echo.
echo Cleaning up...
echo.

REM Delete documentation files
if exist "BUILD-AAB.bat" (
    del "BUILD-AAB.bat"
    echo ✓ Deleted BUILD-AAB.bat
)

if exist "backend\database_schema.sql" (
    del "backend\database_schema.sql"
    echo ✓ Deleted database_schema.sql
)

if exist "backend\StudentFormAPI.postman_collection.json" (
    del "backend\StudentFormAPI.postman_collection.json"
    echo ✓ Deleted postman collection
)

echo.
echo ========================================
echo   Cleanup Complete!
echo ========================================
echo.
echo Your project now contains only:
echo - Source code (app/, backend/)
echo - README.md
echo - .aab file
echo - Build configuration
echo - Gradle wrapper
echo.

pause
