package com.gestion.almacenes.app.presentation.controllers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schema")
public class SchemaController {

    @GetMapping("/{dtoClass}")
    public JsonSchema getSchema(@PathVariable String dtoClass) throws ClassNotFoundException, JsonMappingException {
        // Obtiene la clase del DTO dinámicamente
        Class<?> clazz = Class.forName("com.gestion.almacenes.app.presentation.dtos." + dtoClass);

        // Genera el JSON Schema
        ObjectMapper mapper = new ObjectMapper();
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        return schemaGen.generateSchema(clazz);
    }
}