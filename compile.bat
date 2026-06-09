@echo off
SetLocal EnableDelayedExpansion
set ROOT=%~dp0

if exist "%ROOT%out" rmdir /s /q "%ROOT%out"
mkdir "%ROOT%out"

set FX_LIB=%ROOT%lib
if exist "C:\javafx-sdk-21.0.11\lib" set FX_LIB=C:\javafx-sdk-21.0.11\lib

set SRCFILES=
for %%F in ("%ROOT%com\mycompany\try1\*.java") do (
  set SRCFILES=!SRCFILES! "%%~fF"
)

javac -d "%ROOT%out" --module-path "%FX_LIB%" --add-modules javafx.controls,javafx.graphics,javafx.base !SRCFILES!

if %ERRORLEVEL% NEQ 0 (
  echo Compile failed.
  exit /b %ERRORLEVEL%
)

echo Compile finished successfully.
