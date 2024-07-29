package com.gestion.almacenes.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.dtos.StorehouseTypeDto;
import com.gestion.almacenes.entities.StorehouseType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class StorehouseTypeMapper {

  public StorehouseType fromDto(StorehouseTypeDto dto, StorehouseType storehouseTypeFound) {
    StorehouseType storehouseType = new StorehouseType();
    if (storehouseTypeFound != null) {
      storehouseType = storehouseTypeFound;
    }
    storehouseType.setCode(dto.getCode());
    storehouseType.setName(dto.getName());

    return storehouseType;
  }

  public PagePojo<StorehouseType> fromEntity(Page<StorehouseType> page) {
    PagePojo<StorehouseType> dto = new PagePojo<>();
    dto.setContent(page.getContent());
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }
}
