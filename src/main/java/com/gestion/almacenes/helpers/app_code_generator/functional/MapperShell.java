package com.gestion.almacenes.helpers.app_code_generator.functional;

import com.gestion.almacenes.helpers.app_code_generator.ConfigShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.ToolsShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.DtoShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.EntityShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.PojoShell;
import lombok.Getter;

/**
 * Clase MapperShell
 * Contiene los atributos para generar un Mapper
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
@Getter
public class MapperShell extends ToolsShell {
    String nameMapper;
    String nameMapperLC;
    EntityShell entityShell;
    DtoShell dtoShell;
    PojoShell pojoShell;
    StringBuilder dataSettersPojo;
    StringBuilder dataSettersEntity;

    public MapperShell(EntityShell entityShell, DtoShell dtoShell, PojoShell pojoShell) {
        this.entityShell = entityShell;
        this.dtoShell = dtoShell;
        this.pojoShell = pojoShell;
        this.nameMapper = entityShell.getNameEntity()+"Mapper_bk";
        this.nameMapperLC = UtilShell.getFirstLetterLowerCase(this.nameMapper);
        this.fillByAttributesPojo();
        this.fillByAttributesEntity();


    }
    public StringBuilder generateStringMapper() {
        StringBuilder builder = new StringBuilder();
        builder.append(ConfigShell.MAPPER_PACKAGE).append("\n\n")
                .append("import com.gestion.almacenes.commons.util.PagePojo;\n")
                .append("import com.gestion.almacenes.dtos.").append(dtoShell.getNameDto()).append(";\n")
                .append("import com.gestion.almacenes.entities.").append(entityShell.getNameEntity()).append(";\n")
                .append("import com.gestion.almacenes.pojos.").append(pojoShell.getNamePojo()).append(";\n")
                .append("import org.springframework.data.domain.Page;\n")
                .append("import org.springframework.stereotype.Component;\n\n")
                .append("import java.util.List;\n")
                .append("import java.util.stream.Collectors;\n\n")
                .append("@Component\n")
                .append("public class ").append(this.nameMapper).append(" extends GenericMapper {\n\n")
                .append("  public ").append(entityShell.getNameEntity()).append(" fromDtoToEntity(").append(dtoShell.getNameDto()).append(" dto, ").append(entityShell.getNameEntity()).append(" entityFound) {\n\n")
                .append("    ").append(entityShell.getNameEntity()).append(" entity = (entityFound != null) ?\n")
                .append("            entityFound :\n")
                .append("            new ").append(entityShell.getNameEntity()).append("();\n\n")
                .append(dataSettersEntity)
                .append("    return entity;\n")
                .append("  }\n\n")
                .append("  public ").append(pojoShell.getNamePojo()).append(" fromEntityToPojo(").append(entityShell.getNameEntity()).append(" entity) {\n")
                .append("    ").append(pojoShell.getNamePojo()).append(" pojo = new ").append(pojoShell.getNamePojo()).append("();\n")
                .append(dataSettersPojo)
                .append("    return pojo;\n")
                .append("  }\n\n")
                .append("  public List<").append(pojoShell.getNamePojo()).append("> fromEntityListToPojoList(List<").append(entityShell.getNameEntity()).append("> entityList){\n")
                .append("    return  entityList.stream()\n")
                .append("            .map(this::fromEntityToPojo)\n")
                .append("            .collect(Collectors.toList());\n")
                .append("  }\n\n")
                .append("  public PagePojo<").append(pojoShell.getNamePojo()).append("> toPagePojo(Page<").append(entityShell.getNameEntity()).append("> page) {\n")
                .append("    PagePojo<").append(pojoShell.getNamePojo()).append("> dto = new PagePojo<>();\n\n")
                .append("    dto.setContent(fromEntityListToPojoList(page.getContent()));\n")
                .append("    dto.setLast(page.isLast());\n")
                .append("    dto.setPageNumber(page.getNumber());\n")
                .append("    dto.setPageSize(page.getSize());\n")
                .append("    dto.setTotalPages(page.getTotalPages());\n")
                .append("    dto.setTotalElements(page.getTotalElements());\n")
                .append("    return dto;\n")
                .append("  }\n\n")
                .append("}\n");
        return builder;
    }


    public void fillByAttributesPojo(){
        StringBuilder bodyV1 = new StringBuilder();
        entityShell.getAtributesShellList().forEach(attributesShell -> {
            String attributeUpperCase = UtilShell.getFirstLetterUpperCase(attributesShell.getNameAtributes());
            bodyV1.append(
                    (this.isTypeValid(attributesShell.getTypeAtributes())) ?
                            "    pojo.set"+attributeUpperCase+"( entity.get"+attributeUpperCase+"() );":
                            "    pojo.set"+attributeUpperCase+"Id( entity.get"+attributeUpperCase+"Id() );"
            );
            bodyV1.append("\n");
        });
        dataSettersPojo = bodyV1;
    }

    public void fillByAttributesEntity(){
        StringBuilder bodyV1 = new StringBuilder();
        entityShell.getAtributesShellList().forEach(attributesShell -> {
            String attributeUpperCase = UtilShell.getFirstLetterUpperCase(attributesShell.getNameAtributes());
            bodyV1.append(
                    (this.isTypeValid(attributesShell.getTypeAtributes())) ?
                            "    entity.set"+attributeUpperCase+"( dto.get"+attributeUpperCase+"() );":
                            "    entity.set"+attributeUpperCase+"Id( dto.get"+attributeUpperCase+"Id() );"
            );
            bodyV1.append("\n");
        });
        dataSettersEntity = bodyV1;
    }
}
