@echo off
echo Compilando el proyecto DiezMil con la libreria RMIMVC...
if not exist out mkdir out
dir /s /b src\*.java > sources.txt
javac -cp LibreriaRMIMVC.jar -d out @sources.txt
del sources.txt
echo Compilacion terminada.
pause
