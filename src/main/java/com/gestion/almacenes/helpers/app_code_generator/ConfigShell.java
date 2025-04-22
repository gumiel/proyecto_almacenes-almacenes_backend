package com.gestion.almacenes.helpers.app_code_generator;

/**
 * Clase ConfigShell
 * Contiene las rutas y paquetes de los archivos a generar
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
public class ConfigShell {

    private ConfigShell() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ENTITIES_NAME_PATH = "src/main/java/com/gestion/almacenes/entities"; // NO TIENE '/' AL FINAl
    public static final String ENTITIES_PACKAGE_SIMPLE = "com.gestion.almacenes.entities.";

    public static final String DTO_NAME_PATH = "src/main/java/com/gestion/almacenes/dtos/";
    public static final String DTO_PACKAGE = "com.gestion.almacenes.dtos;";

    public static final String FILTER_NAME_PATH = "src/main/java/com/gestion/almacenes/filters/";
    public static final String FILTER_PACKAGE = "package com.gestion.almacenes.filters;";

    public static final String POJO_NAME_PATH = "src/main/java/com/gestion/almacenes/pojos/";
    public static final String POJO_PACKAGE = "package com.gestion.almacenes.pojos;";

    public static final String SERVICE_NAME_PATH = "src/main/java/com/gestion/almacenes/services/";
    public static final String SERVICE_PACKAGE = "package com.gestion.almacenes.services;";

    public static final String CONTROLLER_NAME_PATH = "src/main/java/com/gestion/almacenes/controllers/";
    public static final StringBuilder CONTROLLER_PACKAGE = new StringBuilder("package com.gestion.almacenes.controllers;");

    public static final String MAPPER_NAME_PATH = "src/main/java/com/gestion/almacenes/mappers/";
    public static final String MAPPER_PACKAGE = "package com.gestion.almacenes.mappers;";

    public static final String REPOSITORY_NAME_PATH = "src/main/java/com/gestion/almacenes/repositories/";
    public static final String REPOSITORY_PACKAGE = "package com.gestion.almacenes.repositories;";

    public static final String IMPLEMENT_NAME_PATH = "src/main/java/com/gestion/almacenes/servicesImpls/";
    public static final String IMPLEMENT_PACKAGE = "package com.gestion.almacenes.servicesImpls;";


}

