package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StorehouseTypeDto;
import com.gestion.almacenes.app.domain.entities.StorehouseType;
import com.gestion.almacenes.app.presentation.pojos.StorehouseTypePojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StorehouseTypeMapper extends GenericMapper{

  public StorehouseType fromDtoToEntity(StorehouseTypeDto dto, StorehouseType entityFound) {

    StorehouseType entity = (entityFound != null) ?
            entityFound :
            new StorehouseType();

    entity.setCode( dto.getCode() );
    entity.setName( dto.getName() );
    return entity;
  }

  public StorehouseTypePojo fromEntityToPojo(StorehouseType entity) {
    StorehouseTypePojo pojo = new StorehouseTypePojo();
    pojo.setId( entity.getId() );
    pojo.setCode( entity.getCode() );
    pojo.setName( entity.getName() );
    pojo.setActive( entity.getActive());
    pojo.setCreatedBy( entity.getCreatedBy());
    pojo.setCreatedDate( entity.getCreatedDate());
    pojo.setLastModifiedBy( entity.getLastModifiedBy());
    pojo.setLastModifiedDate( entity.getLastModifiedDate());
    return pojo;
  }

  public List<StorehouseTypePojo> fromEntityListToPojoList(List<StorehouseType> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<StorehouseTypePojo> toPagePojo(Page<StorehouseType> page) {
    PagePojo<StorehouseTypePojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
