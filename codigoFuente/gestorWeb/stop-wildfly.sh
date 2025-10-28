#!/bin/bash

export JAVA_HOME="/Users/macuco/Library/Java/JavaVirtualMachines/temurin-1.8.0_442/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

JBOSS_CLI="/Users/macuco/Documents/proyectos/t4g/gestor/wildfly-10.1.0.Final/bin/jboss-cli.sh"

"$JBOSS_CLI" --connect --command=":shutdown"
