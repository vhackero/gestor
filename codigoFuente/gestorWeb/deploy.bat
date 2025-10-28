@echo off
setlocal

REM 👉 Forzar uso de Java 8
set "JAVA_HOME=C:\Users\macuco\Library\Java\JavaVirtualMachines\temurin-1.8.0_442\Contents\Home"
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Configuración
set "WAR_NAME=plataforma.war"
set "WAR_PATH=target\%WAR_NAME%"
set "DEPLOY_PATH=C:\Users\macuco\Documents\proyectos\t4g\gestor\wildfly-10.1.0.Final\standalone\deployments"
set "JBOSS_CLI=C:\Users\macuco\Documents\proyectos\t4g\gestor\wildfly-10.1.0.Final\bin\jboss-cli.bat"
set "WILDFLY_BIN=C:\Users\macuco\Documents\proyectos\t4g\gestor\wildfly-10.1.0.Final\bin\standalone.bat"

REM 1. Compilar el WAR con Maven usando Java 8
mvn clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al compilar con Maven
    exit /b 1
)

REM 2. Verificar si WildFly está corriendo
tasklist /FI "IMAGENAME eq java.exe" | findstr /I "wildfly"
IF %ERRORLEVEL% EQU 0 (
    echo 🔁 WildFly ya está corriendo. Desplegando con CLI...
) ELSE (
    echo 🚀 WildFly no está corriendo. Iniciando...
    start "" "%WILDFLY_BIN%"
    timeout /t 10 >nul
)

REM 3. Desplegar usando jboss-cli
"%JBOSS_CLI%" --connect --command="deploy %WAR_PATH% --force"
IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Error al desplegar con jboss-cli
    exit /b 1
)

REM 4. Validar estado del despliegue
"%JBOSS_CLI%" --connect --command="/deployment=%WAR_NAME%:read-attribute(name=status)"

echo ✅ Despliegue completado.
endlocal
