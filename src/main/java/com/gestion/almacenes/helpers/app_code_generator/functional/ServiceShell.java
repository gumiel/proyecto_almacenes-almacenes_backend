package com.gestion.almacenes.helpers.app_code_generator.functional;

import com.gestion.almacenes.helpers.app_code_generator.ConfigShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.DtoShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.EntityShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.FilterShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.PojoShell;
import lombok.Getter;

/**
 * Clase ServiceShell
 * Contiene los atributos para generar un servicio
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
@Getter
public class ServiceShell {

    String nameService;
    String nameServiceLC;
    EntityShell entityShell;
    DtoShell dtoShell;
    PojoShell pojoShell;
    FilterShell filterShell;

    public ServiceShell(EntityShell entityShell, DtoShell dtoShell, FilterShell filterShell, PojoShell pojoShell) {
        this.entityShell = entityShell;
        this.dtoShell = dtoShell;
        this.filterShell = filterShell;
        this.pojoShell = pojoShell;
        this.nameService = entityShell.getNameEntity()+"Service_bk";
        this.nameServiceLC = UtilShell.getFirstLetterLowerCase(this.nameService);
    }

    public StringBuilder generateStringService(){
        StringBuilder builder = new StringBuilder();
        return builder
                .append(ConfigShell.SERVICE_PACKAGE).append("\n\n")
                .append("import com.gestion.almacenes.commons.util.PagePojo;\n")
                .append("import com.gestion.almacenes.dtos.").append(dtoShell.getNameDto()).append(";\n")
                .append("import com.gestion.almacenes.entities.").append(entityShell.getNameEntity()).append(";\n")
                .append("import com.gestion.almacenes.filters.").append(filterShell.getNameFilter()).append(";\n")
                .append("import com.gestion.almacenes.pojos.").append(pojoShell.getNamePojo()).append(";\n")
                .append("\n")
                .append("import java.util.List;\n")
                .append("\n")
                .append("public interface ").append(entityShell.getNameEntity()).append("Service_bk {\n")
                .append("\n")
                .append("  List<").append(pojoShell.getNamePojo()).append("> getAll();\n")
                .append("\n")
                .append("  ").append(pojoShell.getNamePojo()).append(" create(").append(dtoShell.getNameDto()).append(" dto);\n")
                .append("\n")
                .append("  ").append(pojoShell.getNamePojo()).append(" update(Integer id, ").append(dtoShell.getNameDto()).append(" dto);\n")
                .append("\n")
                .append("  ").append(pojoShell.getNamePojo()).append(" getById(Integer id);\n")
                .append("\n")
                .append("  void delete(Integer id);\n")
                .append("\n")
                .append("  void disabled(Integer id);\n")
                .append("\n")
                .append("  PagePojo<").append(pojoShell.getNamePojo()).append("> pageable(Integer page, Integer size, String sortField, String sortOrder,\n")
                .append("      ").append(filterShell.getNameFilter()).append(" filter);\n")
                .append("\n")
                .append("}\n");
    }

}
