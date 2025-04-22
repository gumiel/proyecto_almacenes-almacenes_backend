package com.gestion.almacenes.helpers.app_dictionary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/dictionary")
@Tag(name = "DictionaryController", description = "Generacion de markdown")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Operation(summary = "Generar el markdown")
    @GetMapping("/generatedMarkDown")
    public ResponseEntity<String> generatedMarkDown() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(
                dictionaryService.generatedMarkDown()
        );
    }


}
