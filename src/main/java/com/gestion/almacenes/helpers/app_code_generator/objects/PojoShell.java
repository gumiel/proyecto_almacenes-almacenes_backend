package com.gestion.almacenes.helpers.app_code_generator.objects;

import com.gestion.almacenes.helpers.app_code_generator.ConfigShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase PojoShell
 * Contiene los atributos para generar un pojo
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
@Getter
public class PojoShell {
    String namePojo;
    String namePojoLC;
    String namePackage;
    List<StringBuilder> importList;
    List<StringBuilder> anottationList;
    String startPojo;
    String endPojo;
    StringBuilder body;
    EntityShell entityShell;

    public PojoShell(EntityShell entityShell) {
        this.entityShell = entityShell;
        this.namePojo = entityShell.getNameEntity()+"Pojo_bk";
        this.constructHeader();
        this.body = new StringBuilder();
        this.namePojoLC = UtilShell.getFirstLetterLowerCase(this.namePojo);
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
        this.namePackage = ConfigShell.POJO_PACKAGE+"\n\n";
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
        importList.add(new StringBuilder("import lombok.NoArgsConstructor;\n"));
        importList.add(new StringBuilder("\n"));
    }

    public void fillAnottations() {

        anottationList = new ArrayList<>();
        anottationList.add(new StringBuilder("@Getter\n"));
        anottationList.add(new StringBuilder("@Setter\n"));
        anottationList.add(new StringBuilder("@NoArgsConstructor\n"));
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
        startPojo = "public class " + namePojo + " {\n";
    }

    public void createEndDto() {
        endPojo = "}\n";
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
        dtoBuilder.append(startPojo);
        this.fillBody();
        dtoBuilder.append(body);
        dtoBuilder.append(endPojo);
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
