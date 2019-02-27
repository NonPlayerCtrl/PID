@echo off

set MYJAVA=C:\Program Files (x86)\Java\jre1.8.0_60\bin\java.exe
rem set MYJAVA=C:\Program Files\Java\jre1.8.0_25\bin\java.exe
echo #####################################################
echo using java runtime: "%MYJAVA%"
echo #####################################################
REM @echo on
"%MYJAVA%" -ea -jar download_and_reboot.jar --iec --view 192.168.23.43
pause