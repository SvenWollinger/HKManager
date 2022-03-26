@echo off
set jpackage="%userprofile%\.jdks\openjdk-16.0.1\bin\jpackage.exe"
%jpackage% @jpackage.txt --main-class io.wollinger.hkmanager.Main --app-version @version.txt --type exe --license-file LICENSE --win-dir-chooser --win-menu --win-shortcut
%jpackage% @jpackage.txt --main-class io.wollinger.hkmanager.Main --app-version @version.txt --type app-image
