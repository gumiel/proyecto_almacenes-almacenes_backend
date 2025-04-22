package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.UnitMeasurementDto;
import com.gestion.almacenes.app.domain.entities.UnitMeasurement;
import com.gestion.almacenes.app.presentation.pojos.UnitMeasurementPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnitMeasurementMapper extends GenericMapper {

  public UnitMeasurement fromDtoToEntity(UnitMeasurementDto dto, UnitMeasurement entityFound) {

    UnitMeasurement entity = (entityFound != null) ?
            entityFound :
            new UnitMeasurement();

    entity.setCode( dto.getCode() );
    entity.setName( dto.getName() );
    return entity;
  }

  public UnitMeasurementPojo fromEntityToPojo(UnitMeasurement entity) {
    UnitMeasurementPojo pojo = new UnitMeasurementPojo();
    pojo.setId( entity.getId() );
    pojo.setCode( entity.getCode() );
    pojo.setName( entity.getName() );
    return pojo;
  }

  public List<UnitMeasurementPojo> fromEntityListToPojoList(List<UnitMeasurement> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<UnitMeasurementPojo> toPagePojo(Page<UnitMeasurement> page) {
    PagePojo<UnitMeasurementPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
