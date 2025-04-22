package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.CatalogProductStorehouseDto;
import com.gestion.almacenes.app.domain.entities.CatalogProductStorehouse;
import com.gestion.almacenes.app.presentation.pojos.CatalogProductStorehousePojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatalogProductStorehouseMapper extends GenericMapper {

  public CatalogProductStorehouse fromDtoToEntity(CatalogProductStorehouseDto dto, CatalogProductStorehouse entityFound) {

    CatalogProductStorehouse entity = (entityFound != null) ?
            entityFound :
            new CatalogProductStorehouse();

    entity.setId( dto.getId() );

    return entity;
  }

  public CatalogProductStorehousePojo fromEntityToPojo(CatalogProductStorehouse entity) {
    CatalogProductStorehousePojo pojo = new CatalogProductStorehousePojo();
    pojo.setId( entity.getId() );
    pojo.setStorehouseId( entity.getStorehouse().getId() );
    pojo.setProductId( entity.getProduct().getId() );
    return pojo;
  }

  public List<CatalogProductStorehousePojo> fromEntityListToPojoList(List<CatalogProductStorehouse> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<CatalogProductStorehousePojo> toPagePojo(Page<CatalogProductStorehouse> page) {
    PagePojo<CatalogProductStorehousePojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
