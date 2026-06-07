@echo off
SetLocal EnableDelayedExpansion
set ROOT=%~dp0
if exist "%ROOT%out" rmdir /s /q "%ROOT%out"
mkdir "%ROOT%out"

set SRCFILES=
for %%F in ("%ROOT%com\mycompany\try1\*.java") do (
  set SRCFILES=!SRCFILES! "%%~fF"
)

javac -d "%ROOT%out" --module-path "%ROOT%lib" --add-modules javafx.controls,javafx.graphics,javafx.base !SRCFILES!

if %ERRORLEVEL% NEQ 0 (
  echo Compile failed.
  exit /b %ERRORLEVEL%
)

echo Compile finished successfully.
