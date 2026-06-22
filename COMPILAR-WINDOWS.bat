@echo off
title OnePieceCraft - Compilador do Mod
color 0A

echo ============================================
echo   ONE PIECE CRAFT - Compilador do Mod
echo   Minecraft 1.21.1 + Forge
echo ============================================
echo.
echo Procurando Java no seu computador...
echo.

set JAVA_EXE=

:: 1) Java embutido no TLauncher
for /d %%D in ("%APPDATA%\.tlauncher\java\*") do (
    if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
)
for /d %%D in ("%APPDATA%\tlauncher\java\*") do (
    if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
)
for /d %%D in ("%LOCALAPPDATA%\Programs\tlauncher\*") do (
    if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
)

:: 2) Java do Minecraft oficial
if "%JAVA_EXE%"=="" (
    for /d %%D in ("%APPDATA%\.minecraft\runtime\*\windows-x64\*") do (
        if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
    )
)

:: 3) Java 21 instalado no Windows
if "%JAVA_EXE%"=="" (
    for /d %%D in ("C:\Program Files\Eclipse Adoptium\jdk-21*") do (
        if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
    )
)
if "%JAVA_EXE%"=="" (
    for /d %%D in ("C:\Program Files\Microsoft\jdk-21*") do (
        if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
    )
)
if "%JAVA_EXE%"=="" (
    for /d %%D in ("C:\Program Files\Java\jdk-21*") do (
        if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
    )
)
if "%JAVA_EXE%"=="" (
    for /d %%D in ("C:\Program Files\Java\jdk21*") do (
        if exist "%%D\bin\java.exe" set JAVA_EXE=%%D\bin\java.exe
    )
)

:: 4) Java do sistema
if "%JAVA_EXE%"=="" (
    where java >nul 2>&1
    if not errorlevel 1 set JAVA_EXE=java
)

:: Nao encontrou Java
if "%JAVA_EXE%"=="" (
    echo.
    echo [ERRO] Nenhum Java encontrado!
    echo.
    echo SOLUCAO MAIS FACIL:
    echo  1. Abra o TLauncher
    echo  2. Lance o Minecraft 1.21.1 UMA VEZ
    echo  3. Feche o Minecraft
    echo  4. Execute este arquivo novamente
    echo.
    echo OU baixe Java 21: https://adoptium.net/
    echo ^(Windows x64 .msi - instala com um clique^)
    echo.
    pause
    exit /b 1
)

echo [OK] Java encontrado!
"%JAVA_EXE%" -version 2>&1
echo.

:: Configura JAVA_HOME
for %%F in ("%JAVA_EXE%") do set JAVA_BIN=%%~dpF
set JAVA_HOME=%JAVA_BIN:~0,-5%
set PATH=%JAVA_HOME%\bin;%PATH%

echo ============================================
echo   Compilando... (1a vez: 5-15 minutos)
echo   NAO FECHE ESTA JANELA!
echo ============================================
echo.

call gradlew.bat build --no-daemon -Dorg.gradle.java.home="%JAVA_HOME%"

if errorlevel 1 (
    echo.
    color 0C
    echo [ERRO] Falha na compilacao!
    echo Se der erro de Java: instale Java 21 em https://adoptium.net/
    echo Se der erro de internet: verifique conexao e tente novamente
    pause
    exit /b 1
)

echo.
color 0A
echo ============================================
echo   MOD COMPILADO COM SUCESSO!
echo ============================================
echo.
echo Arquivo: build\libs\onepiececraft-1.0.0.jar
echo.

start "" "build\libs"
pause >nul

if not exist "%APPDATA%\.minecraft\mods" mkdir "%APPDATA%\.minecraft\mods"
start "" "%APPDATA%\.minecraft\mods"

echo Copie o .jar para a pasta mods que abriu!
echo.
