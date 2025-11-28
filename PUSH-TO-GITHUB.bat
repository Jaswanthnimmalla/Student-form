@echo off
title Push to GitHub
color 0B

echo.
echo ========================================
echo   Push Student Form App to GitHub
echo ========================================
echo.
echo Repository: https://github.com/Jaswanthnimmalla/Student-form
echo.

pause

echo.
echo Step 1: Initializing Git...
git init

echo.
echo Step 2: Configuring Git...
git config user.name "Jaswanth Nimmalla"
git config user.email "jaswanthnimmalla@gmail.com"

echo.
echo Step 3: Adding all files...
git add .

echo.
echo Step 4: Creating commit...
git commit -m "Complete Student Form App with Android frontend, Spring Boot backend, and responsive UI"

echo.
echo Step 5: Setting up remote...
git remote remove origin 2>nul
git remote add origin https://github.com/Jaswanthnimmalla/Student-form.git

echo.
echo Step 6: Setting branch to main...
git branch -M main

echo.
echo Step 7: Pushing to GitHub...
echo.
echo ⚠️ You may be asked for GitHub credentials:
echo - Username: Jaswanthnimmalla
echo - Password: Use Personal Access Token (not your GitHub password)
echo.
echo To create token: https://github.com/settings/tokens
echo.

git push -u origin main

echo.
echo ========================================
echo   Done!
echo ========================================
echo.
echo Check your repository:
echo https://github.com/Jaswanthnimmalla/Student-form
echo.

pause
