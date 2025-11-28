@echo off
title Build Signed Android App Bundle (.aab)
color 0A

echo.
echo ========================================
echo   Building Signed Android App Bundle
echo ========================================
echo.

echo Step 1: Cleaning previous builds...
call gradlew.bat clean

echo.
echo Step 2: Building release bundle...
call gradlew.bat bundleRelease

echo.
echo ========================================
echo   Build Complete!
echo ========================================
echo.

if exist "app\build\outputs\bundle\release\app-release.aab" (
    echo SUCCESS! Your .aab file is ready at:
    echo app\build\outputs\bundle\release\app-release.aab
    echo.
    echo File size:
    dir "app\build\outputs\bundle\release\app-release.aab" | find "app-release.aab"
    echo.
    echo ========================================
    echo   Next Steps:
    echo ========================================
    echo 1. Sign the bundle using Android Studio:
    echo    - Build ^> Generate Signed Bundle / APK
    echo    - Password: studentform2025
    echo.
    echo 2. Or move to images folder:
    echo    mkdir images\app-bundle
    echo    copy app\build\outputs\bundle\release\app-release.aab images\app-bundle\
    echo.
) else (
    echo ERROR: Build failed! Check the errors above.
    echo.
)

pause
