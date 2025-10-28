#!/bin/bash

# 👉 Forzar uso de Java 8
export JAVA_HOME="/Users/macuco/Library/Java/JavaVirtualMachines/temurin-1.8.0_442/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

# Configuración
WAR_NAME="plataforma.war"
WAR_PATH="target/$WAR_NAME"
DEPLOY_PATH="/Users/macuco/Documents/proyectos/t4g/gestor/wildfly-10.1.0.Final/standalone/deployments"
JBOSS_CLI="/Users/macuco/Documents/proyectos/t4g/gestor/wildfly-10.1.0.Final/bin/jboss-cli.sh"
WILDFLY_BIN="/Users/macuco/Documents/proyectos/t4g/gestor/wildfly-10.1.0.Final/bin/standalone.sh"

# 1. Compilar el WAR con Maven usando Java 8
mvn clean package -DskipTests || { echo "❌ Error al compilar con Maven"; exit 1; }

# 2. Verificar si WildFly está corriendo
if pgrep -f "wildfly.*standalone"; then
    echo "🔁 WildFly ya está corriendo. Desplegando con CLI..."
else
    echo "🚀 WildFly no está corriendo. Iniciando..."
    nohup "$WILDFLY_BIN" > wildfly.log 2>&1 &
    sleep 10
fi

# 3. Desplegar usando jboss-cli
"$JBOSS_CLI" --connect --command="deploy $WAR_PATH --force" || {
    echo "❌ Error al desplegar con jboss-cli"; exit 1;
}

# 4. Validar estado del despliegue
"$JBOSS_CLI" --connect --command="/deployment=$WAR_NAME:read-attribute(name=status)"

echo "✅ Despliegue completado."
