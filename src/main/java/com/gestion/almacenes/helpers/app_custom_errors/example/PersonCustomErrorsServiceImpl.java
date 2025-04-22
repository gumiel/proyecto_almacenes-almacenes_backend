package com.gestion.almacenes.helpers.app_custom_errors.example;

import com.gestion.almacenes.app.presentation.dtos.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gestion.almacenes.helpers.app_custom_errors.CustomErrorsLib.*;

@Service
@AllArgsConstructor
public class PersonCustomErrorsServiceImpl {

    public PersonCustomErrorsDto create(PersonCustomErrorsDto personCustomErrorsDto){
        if (personCustomErrorsDto.getCode()!=null && personCustomErrorsDto.getCode().equals("CODE-1")) {
            errorDuplicateInField(PersonCustomErrorsDto.class, "code", personCustomErrorsDto.getCode());
        }
        if (personCustomErrorsDto.getCode()!=null && personCustomErrorsDto.getCode().equals("CODE-2")) {
            errorDuplicateInFieldCode(PersonCustomErrorsDto.class, "code", personCustomErrorsDto.getCode());
        }

        return personCustomErrorsDto;
    }

}
