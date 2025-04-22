package com.gestion.almacenes.helpers.app_code_generator.commons;

import java.util.Objects;

/**
 * Clase ToolsShell
 * Contiene los metodos que tendran todas las clases
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
public class ToolsShell {

    public String convertType(String type){
        return ( !this.isTypeValid(type) ) ? "Integer": type;
    }

    public boolean isTypeValid(String type){
        return (
                Objects.equals(type, "Integer") ||
                        Objects.equals(type, "String") ||
                        Objects.equals(type, "Boolean") ||
                        Objects.equals(type, "Double") ||
                        Objects.equals(type, "LocalTime") ||
                        Objects.equals(type, "LocalDate") ||
                        Objects.equals(type, "BigDecimal")
        );
    }

}
