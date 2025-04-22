package com.gestion.almacenes.helpers.app_code_generator.functional;

import com.gestion.almacenes.helpers.app_code_generator.ConfigShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.ToolsShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.DtoShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.EntityShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.FilterShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.PojoShell;

/**
 * Clase ControllerShell
 * Contiene los atributos y métodos para generar el controlador de un módulo
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
public class ControllerShell extends ToolsShell {

    String nameController;
    String nameControllerLC;
    EntityShell entityShell;
    DtoShell dtoShell;
    FilterShell filterShell;
    ServiceShell serviceShell;
    StringBuilder dataFilterRequestParam;
    StringBuilder dataFilter;
    PojoShell pojoShell;

    public ControllerShell(EntityShell entityShell, DtoShell dtoShell, FilterShell filterShell, ServiceShell serviceShell, PojoShell pojoShell) {
        this.entityShell = entityShell;
        this.dtoShell = dtoShell;
        this.filterShell = filterShell;
        this.serviceShell = serviceShell;
        this.pojoShell = pojoShell;
        this.nameController = entityShell.getNameEntity()+"Controller_bk";
        this.nameControllerLC = UtilShell.getFirstLetterLowerCase(this.nameController);
        this.fillByDataFilterIntoRequestParameter();
        this.fillByFilter();

    }

    public StringBuilder generateStringController() {

        String entityShellName = entityShell.getNameEntity();
        String entityShellNameLC = UtilShell.getFirstLetterLowerCase(entityShellName);
        String dtoShellName = dtoShell.getNameDto();
        String dtoShellNameLC = UtilShell.getFirstLetterLowerCase(dtoShell.getNameDto());
        String filterShellName = filterShell.getNameFilter();
        String filterShellNameLC = UtilShell.getFirstLetterLowerCase(filterShellName);
        String pojoShellName = pojoShell.getNamePojo();
        String pojoShellNameLC = UtilShell.getFirstLetterLowerCase(pojoShellName);
        String serviceShellName = serviceShell.getNameService();
        String serviceShellNameLC = UtilShell.getFirstLetterLowerCase(serviceShellName);

        StringBuilder builder = new StringBuilder();
        return builder
                .append(ConfigShell.CONTROLLER_PACKAGE).append("\n\n")
                .append("import com.gestion.almacenes.commons.ResponseEntityGeneric;\n")
                .append("import com.gestion.almacenes.commons.util.PagePojo;\n")
                .append("import com.gestion.almacenes.dtos.").append(dtoShellName).append(";\n")
                .append("import com.gestion.almacenes.entities.").append(entityShellName).append(";\n")
                .append("import com.gestion.almacenes.filters.").append(filterShellName).append(";\n")
                .append("import com.gestion.almacenes.pojos.").append(pojoShellName).append(";\n")
                .append("import com.gestion.almacenes.services.").append(serviceShellName).append(";\n")
                .append("import io.swagger.v3.oas.annotations.Operation;\n")
                .append("import io.swagger.v3.oas.annotations.tags.Tag;\n")
                .append("import jakarta.validation.Valid;\n")
                .append("import lombok.AllArgsConstructor;\n")
                .append("import org.springframework.http.ResponseEntity;\n")
                .append("import org.springframework.web.bind.annotation.*;\n")
                .append("\n")
                .append("import java.util.List;\n")
                .append("\n")
                .append("@AllArgsConstructor\n")
                .append("@RestController\n")
                .append("@RequestMapping(\"/").append(entityShellNameLC).append("_bk\")\n")
                .append("@Tag(name = \"").append(entityShellName).append("_bk\", description = \"Gestión de productos. Los productos son los unicos que podran ingresar o salir a un almacen\")\n")
                .append("public class ").append(this.nameController).append(" {\n")
                .append("\n")
                .append("  private final ").append(serviceShellName).append(" ").append(serviceShellNameLC).append(";\n")
                .append("\n")
                .append("  @Operation(summary = \"Obtener todos los registros\")\n")
                .append("  @GetMapping\n")
                .append("  public ResponseEntity<List<").append(pojoShellName).append(">> getAll() {\n")
                .append("\n")
                .append("    return ResponseEntityGeneric.statusGET(\n")
                .append("        ").append(serviceShellNameLC).append(".getAll()\n")
                .append("    );\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("  @Operation(summary = \"Creación del registro\")\n")
                .append("  @PostMapping\n")
                .append("  public ResponseEntity<").append(pojoShellName).append("> create(@Valid @RequestBody ").append(dtoShellName).append(" dto) {\n")
                .append("\n")
                .append("    return ResponseEntityGeneric.statusPOST(\n")
                .append("        ").append(serviceShellNameLC).append(".create(dto)\n")
                .append("    );\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("  @Operation(summary = \"Edición del registro\")\n")
                .append("  @PutMapping(\"/{id}\")\n")
                .append("  public ResponseEntity<").append(pojoShellName).append("> update(@PathVariable Integer id,\n")
                .append("      @Valid @RequestBody ").append(dtoShellName).append(" dto) {\n")
                .append("\n")
                .append("    return ResponseEntityGeneric.statusPUT(\n")
                .append("        ").append(serviceShellNameLC).append(".update(id, dto)\n")
                .append("    );\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("  @Operation(summary = \"Obtención de los datos del registro por el identificador\")\n")
                .append("  @GetMapping(\"/{id}\")\n")
                .append("  public ResponseEntity<").append(pojoShellName).append("> getById(@PathVariable Integer id) {\n")
                .append("\n")
                .append("    return ResponseEntityGeneric.statusGET(\n")
                .append("        ").append(serviceShellNameLC).append(".getById(id)\n")
                .append("    );\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("  @Operation(summary = \"Eliminación del registro por el identificador\")\n")
                .append("  @DeleteMapping(\"/{id}\")\n")
                .append("  public ResponseEntity<Void> delete(@PathVariable Integer id) {\n")
                .append("\n")
                .append("    ").append(serviceShellNameLC).append(".delete(id);\n")
                .append("    return ResponseEntityGeneric.statusDELETE();\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("  @Operation(summary = \"Deshabilitar del registro por el identificador\")\n")
                .append("  @DeleteMapping(\"/disabled/{id}\")\n")
                .append("  public ResponseEntity<Void> disabled(@PathVariable Integer id) {\n")
                .append("\n")
                .append("    ").append(serviceShellNameLC).append(".disabled(id);\n")
                .append("    return ResponseEntityGeneric.statusDELETE();\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("  @Operation(summary = \"Paginador y buscador de registros por atributos\")\n")
                .append("  @GetMapping(\"/pageable\")\n")
                .append("  public ResponseEntity<PagePojo<").append(pojoShellName).append(">> pageable(\n")
                .append("      @RequestParam(defaultValue = \"0\") int page,\n")
                .append("      @RequestParam(defaultValue = \"5\") int size,\n")
                .append("      @RequestParam(defaultValue = \"id\") String sortField,\n")
                .append("      @RequestParam(defaultValue = \"asc\") String sortOrder,\n")
                .append(dataFilterRequestParam)
                .append("  ) {\n")
                .append("\n")
                .append("    ").append(filterShellName).append(" filter = ").append(filterShellName).append(".builder()\n")
                .append(dataFilter)
                .append("            .build();\n")
                .append("    return ResponseEntityGeneric.statusGET(\n")
                .append("        ").append(serviceShellNameLC).append(".pageable(page, size, sortField, sortOrder, filter)\n")
                .append("    );\n")
                .append("\n")
                .append("  }\n")
                .append("\n")
                .append("}\n");
    }

    public void fillByDataFilterIntoRequestParameter() {
        StringBuilder bodyV1 = new StringBuilder();
        entityShell.getAtributesShellList().forEach(atributesShell -> {
            bodyV1.append("      @RequestParam(required = false) ");
            bodyV1.append(
                    this.convertType(atributesShell.getTypeAtributes())
            );
            bodyV1.append(" ");
            bodyV1.append(
                    (this.isTypeValid(atributesShell.getTypeAtributes())) ?
                            atributesShell.getNameAtributes():
                            atributesShell.getNameAtributes()+"Id"
            );
            bodyV1.append(",\n");
        });

        dataFilterRequestParam = bodyV1.length() > 0 ? new StringBuilder(bodyV1.substring(0, bodyV1.length() - 2)) : bodyV1;
    }

    public void fillByFilter(){
        StringBuilder bodyV1 = new StringBuilder();
        entityShell.getAtributesShellList().forEach(atributesShell -> {
            bodyV1.append(
                    (this.isTypeValid(atributesShell.getTypeAtributes())) ?
                            "            ."+atributesShell.getNameAtributes()+"("+atributesShell.getNameAtributes()+")":
                            "            ."+atributesShell.getNameAtributes()+"Id("+atributesShell.getNameAtributes()+"Id)"
            );
            bodyV1.append("\n");
        });
        dataFilter = bodyV1;
    }



}
