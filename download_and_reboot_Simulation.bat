@echo off
set MYJAVA=C:\Program Files (x86)\Java\jre1.8.0_121\bin\java.exe
rem set MYJAVA=C:\Program Files (x86)\Java\jre1.6.0_07\bin\java.exe
rem set MYJAVA=C:\Program Files\Java\jre1.8.0_25\bin\java.exe

rem set MYJAVA=C:\Program Files\Java\jre7\bin\java.exe
REM set MYJAVA=java.exe
echo #####################################################
echo using java runtime: "%MYJAVA%"
echo #####################################################
REM @echo on
"%MYJAVA%" -ea -jar download_and_reboot.jar --local --iec --view .\Sim\
REM "%MYJAVA%" -ea -jar download_and_reboot.jar --fulldownload --local --iec --config --view --odc C:\tmp\download_test
pause

rEM now start simulation again
REM 01.30
IF EXIST Sim\system\bin\Start_Kemro.P0P06CP25x_06.14b.cmd start Sim\system\bin\Start_Kemro.P0P06CP25x_06.14b.cmd
REM 01.32
IF EXIST Sim\system\bin\Start_Kemro.P0P06CP25x_06.16e.cmd start Sim\system\bin\Start_Kemro.P0P06CP25x_06.16e.cmd
REM 01.34
IF EXIST Sim\system\bin\Start_Kemro.P0P06CP25x_06.18b.cmd start Sim\system\bin\Start_Kemro.P0P06CP25x_06.18b.cmd
REM 01.38
IF EXIST Sim\system\bin\Start_Kemro.P0P02CP05x_06.25a.cmd start Sim\system\bin\Start_Kemro.P0P02CP05x_06.25a.cmd
REM 01.41
IF EXIST Sim\system\bin\Start_Kemro.P0P02CP05x_06.28c.cmd start Sim\system\bin\Start_Kemro.P0P02CP05x_06.28c.cmd
REM i1000_01.41
IF EXIST Sim\system\bin\Start_Kemro.P0P02CP03x_06.28c.cmd start Sim\system\bin\Start_Kemro.P0P02CP03x_06.28c.cmd