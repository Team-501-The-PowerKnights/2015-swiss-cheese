@echo off

echo.

REM set ROBOT=woodbot
set ROBOT=robot
echo ROBOT=%ROBOT%

set PATH=C:\Program Files (x86)\PuTTY;%PATH%
REM echo %PATH%

cd C:\F_FIRST\GitRepos\2015repos\%ROBOT%_2015\prefs
dir wpilib-preferences.ini

echo.

echo Copy will take several seconds. Wait until it completes.
echo.
pscp wpilib-preferences.ini lvuser@roboRIO-501.local:/home/lvuser
echo.

PAUSE