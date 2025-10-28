@echo off
setlocal

REM 👉 Forzar uso de Java 8
set "JAVA_HOME=C:\Users\macuco\Library\Java\JavaVirtualMachines\temurin-1.8.0_442\Contents\Home"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Ruta al CLI de WildFly
set "JBOSS_CLI=C:\Users\macuco\Documents\proyectos\t4g\gestor\wildfly-10.1.0.Final\bin\jboss-cli.bat"

REM Detener WildFly de forma limpia
"%JBOSS_CLI%" --connect --command=":shutdown"
IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al intentar detener WildFly
    exit /b 1
)

echo ✅ WildFly detenido correctamente.
endlocal
