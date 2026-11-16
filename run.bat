@echo off
rem ============================================================
rem  Card Battle - one click build and run (Windows)
rem  Requires JDK 17 in PATH. No third party libraries.
rem ============================================================
setlocal
cd /d "%~dp0"

if not exist out mkdir out

echo [1/2] Compiling...
javac -encoding UTF-8 -d out src\core\*.java src\model\*.java src\service\*.java src\view\*.java src\jiagou\*.java
if errorlevel 1 (
    echo.
    echo Build FAILED. Make sure JDK 17 is installed and PATH is set.
    pause
    exit /b 1
)

echo [2/2] Starting...
java -cp out jiagou.Main

echo.
pause
