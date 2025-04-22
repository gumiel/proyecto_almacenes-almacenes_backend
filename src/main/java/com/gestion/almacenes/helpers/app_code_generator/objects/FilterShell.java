package com.gestion.almacenes.helpers.app_code_generator.objects;

import com.gestion.almacenes.helpers.app_code_generator.ConfigShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase FilterShell
 * Contiene los atributos para generar un filtro
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
@Getter
public class FilterShell {
    String nameFilter;
    String nameFilterLC;
    String namePackage;
    List<StringBuilder> importList;
    List<StringBuilder> anottationList;
    String startFilter;
    String endFilter;
    StringBuilder body;
    EntityShell entityShell;

    public FilterShell(EntityShell entityShell) {
        this.entityShell = entityShell;
        this.nameFilter = entityShell.getNameEntity()+"Filter_bk";
        this.constructHeader();
        this.body = new StringBuilder();
        this.nameFilterLC = UtilShell.getFirstLetterLowerCase(this.nameFilter);
    }

    public void constructHeader() {
        this.fillNamePackage();
        this.fillImports();
        this.fillAnottations();
        this.createStartDto();
        this.createEndDto();
        this.fillBody();
    }

    public void fillNamePackage() {
        this.namePackage = ConfigShell.FILTER_PACKAGE+"\n\n";
    }

    public void fillImports() {
        importList = new ArrayList<>();
        importList.add(new StringBuilder("import java.time.LocalDateTime;\n"));
        importList.add(new StringBuilder("import java.math.BigDecimal;\n"));
        importList.add(new StringBuilder("import java.time.LocalDateTime;\n"));
        importList.add(new StringBuilder("import java.time.LocalDate;\n"));
        importList.add(new StringBuilder("import java.time.LocalTime;\n"));
        importList.add(new StringBuilder("import lombok.Getter;\n"));
        importList.add(new StringBuilder("import lombok.Setter;\n"));
        importList.add(new StringBuilder("import lombok.Builder;\n"));
        importList.add(new StringBuilder("import lombok.ToString;\n"));
        importList.add(new StringBuilder("import lombok.NoArgsConstructor;\n"));
        importList.add(new StringBuilder("\n"));
    }

    public void fillAnottations() {

        anottationList = new ArrayList<>();
        anottationList.add(new StringBuilder("@Builder\n"));
        anottationList.add(new StringBuilder("@ToString\n"));
        anottationList.add(new StringBuilder("@Getter\n"));
    }

    public void fillBody() {
        StringBuilder bodyV1 = new StringBuilder();
        entityShell.getAtributesShellList().forEach(atributesShell -> {
            bodyV1.append("private ");
            bodyV1.append(
                    this.convertType(atributesShell.getTypeAtributes())
            );
            bodyV1.append(" ");
            bodyV1.append(
                    (this.isTypeValid(atributesShell.getTypeAtributes())) ?
                            atributesShell.getNameAtributes():
                            atributesShell.getNameAtributes()+"Id"
            );
            bodyV1.append(";\n");
        });
        body = bodyV1;
    }

    public void createStartDto() {
        startFilter = "public class " + nameFilter + " {\n";
    }

    public void createEndDto() {
        endFilter = "}\n";
    }

    public StringBuilder generateDto() {
        StringBuilder dtoBuilder = new StringBuilder();
        dtoBuilder.append(namePackage);
        for (StringBuilder importLine : importList) {
            dtoBuilder.append(importLine);
        }
        for (StringBuilder anottationLine : anottationList) {
            dtoBuilder.append(anottationLine);
        }
        dtoBuilder.append(startFilter);
        this.fillBody();
        dtoBuilder.append(body);
        dtoBuilder.append(endFilter);
        return dtoBuilder;
    }

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
