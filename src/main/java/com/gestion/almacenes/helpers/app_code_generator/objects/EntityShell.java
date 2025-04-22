package com.gestion.almacenes.helpers.app_code_generator.objects;

import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Clase EntityShell
 * Contiene los atributos para generar una entidad
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
@Getter
@Setter
@NoArgsConstructor
public class EntityShell {
    String className; // nombre de clase con todos los paquetes asociados
    String nameEntity;
    String nameEntityLC;
    List<AtributesShell> atributesShellList;

    public EntityShell(String nameEntity) {
        this.nameEntity = nameEntity;
        this.nameEntityLC = UtilShell.getFirstLetterLowerCase(this.nameEntity);
    }


}
