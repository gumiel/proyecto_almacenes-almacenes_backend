package com.gestion.almacenes.helpers.app_code_generator.functional;

import com.gestion.almacenes.helpers.app_code_generator.ConfigShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.ToolsShell;
import com.gestion.almacenes.helpers.app_code_generator.commons.UtilShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.DtoShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.EntityShell;
import com.gestion.almacenes.helpers.app_code_generator.objects.FilterShell;

/**
 * Clase RepositoryShell
 * Contiene los atributos para generar un repositorio
 * Creado por: Henry Perez Gumiel
 * Fecha: 09/03/2025
 */
public class RepositoryShell extends ToolsShell {
    String nameRepository;
    String nameRepositoryLC;
    EntityShell entityShell;
    DtoShell dtoShell;
    FilterShell filterShell;
    public RepositoryShell(EntityShell entityShell, DtoShell dtoShell, FilterShell filterShell) {
        this.entityShell = entityShell;
        this.dtoShell = dtoShell;
        this.filterShell = filterShell;
        this.nameRepository = entityShell.getNameEntity()+"Repository_bk";
        this.nameRepositoryLC = UtilShell.getFirstLetterLowerCase(this.nameRepository);
    }



    public StringBuilder generateStringRepository() {

        String entityShellName = entityShell.getNameEntity();
        String repositoryShellName = this.getNameRepository();

        StringBuilder builder = new StringBuilder();
        builder.append(ConfigShell.REPOSITORY_PACKAGE).append("\n\n")
                .append("import com.gestion.almacenes.entities.").append(entityShellName).append(";\n")
                .append("import org.springframework.data.domain.Page;\n")
                .append("import org.springframework.data.domain.Pageable;\n")
                .append("import org.springframework.data.jpa.domain.Specification;\n")
                .append("import org.springframework.data.jpa.repository.JpaRepository;\n\n")
                .append("import java.util.List;\n")
                .append("import java.util.Optional;\n\n")
                .append("public interface ").append(repositoryShellName).append(" extends JpaRepository<").append(entityShellName).append(", Integer> {\n\n")
                .append("  boolean existsByCodeAndActiveIsTrue(String code);\n\n")
                .append("  List<").append(entityShellName).append("> findByActiveTrue();\n\n")
                .append("  Optional<").append(entityShellName).append("> findByIdAndActiveIsTrue(Integer id);\n\n")
                .append("  Page<").append(entityShellName).append("> findAll(Specification<").append(entityShellName).append("> spec, Pageable pageable);\n")
                .append("}\n");
        return builder;
    }

    public String getNameRepository() {
        return nameRepository;
    }

    public String getNameRepositoryLC() {
        return nameRepositoryLC;
    }
}
