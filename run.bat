@echo off
SetLocal
set ROOT=%~dp0

set FX_LIB=%ROOT%lib
if exist "C:\javafx-sdk-21.0.11\lib" set FX_LIB=C:\javafx-sdk-21.0.11\lib

set FX_BIN=%ROOT%bin
if exist "C:\javafx-sdk-21.0.11\bin" set FX_BIN=C:\javafx-sdk-21.0.11\bin

set PATH=%FX_BIN%;%PATH%

java -cp "%ROOT%out" --module-path "%FX_LIB%" --add-modules javafx.controls,javafx.graphics,javafx.base com.mycompany.try1.MainApp
