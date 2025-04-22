package com.gestion.almacenes.helpers.app_code_generator.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Clase AtributesShell
 * Contiene los atributos de una clase
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
@Getter
@Setter
@NoArgsConstructor
public class AtributesShell {
    String nameAtributes;
    String typeAtributes;
    String importAtributes;
    List<String> anottationList;
}
