@echo off 
cd ..\..\..
call mvn clean compile install
cd ..\wolfman-web\
call mvn clean compile package -Denv=onlinecheck
pause