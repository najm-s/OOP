@echo off
SetLocal
set ROOT=%~dp0

java -cp "%ROOT%out" --module-path "%ROOT%lib" --add-modules javafx.controls,javafx.graphics,javafx.base com.mycompany.try1.MainApp
