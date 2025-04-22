#!/bin/bash

# Definir variables
JAR_PATH="./target/almacenes-0.0.1-SNAPSHOT_test.jar"
LOG_PATH="./almacenes.log"
SPRING_PROFILE="--spring.profiles.active=test"

# Comando para ejecutar la aplicación
start_app() {
    echo "Iniciando aplicación Almacenes..."
    nohup java -jar $JAR_PATH $SPRING_PROFILE
    echo "Aplicación iniciada con PID $!"
}

# Función para detener la aplicación (opcional)
stop_app() {
    echo "Deteniendo aplicación Almacenes..."
    pkill -f $JAR_PATH
    echo "Aplicación detenida."
}

# Verifica si la aplicación ya está corriendo
if pgrep -f $JAR_PATH > /dev/null
then
#    echo "La aplicación ya está corriendo."
    stop_app
    start_app
else
    start_app
fi