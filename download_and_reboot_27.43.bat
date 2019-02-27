@echo off

set MYJAVA=C:\Program Files\Java\jre1.8.0_121\bin\java.exe
rem set MYJAVA=C:\Program Files\Java\jre1.8.0_25\bin\java.exe
echo #####################################################
echo using java runtime: "%MYJAVA%"
echo #####################################################
REM @echo on --view
"%MYJAVA%" -ea -jar download_and_reboot.jar --iec --view 192.168.27.43
pause