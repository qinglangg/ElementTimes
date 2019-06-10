@echo off
echo ####################
echo setupDecompWorkspace
echo ####################
echo.
title setupDecompWorkspace
cmd /c ".\gradlew setupDecompWorkspace"

echo.
echo.
echo.

echo ####################
echo Loading Eclipse
echo ####################
echo.
title eclipse
cmd /c ".\gradlew idea"
cmd /c ".\gradlew genIntellijRuns"
echo Done &pause>nul