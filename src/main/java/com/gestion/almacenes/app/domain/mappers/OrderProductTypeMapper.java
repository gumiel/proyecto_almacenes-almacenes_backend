package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductTypeDto;
import com.gestion.almacenes.app.domain.entities.OrderProductType;
import com.gestion.almacenes.app.presentation.pojos.OrderProductTypePojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderProductTypeMapper extends GenericMapper {

  public OrderProductType fromDtoToEntity(OrderProductTypeDto dto, OrderProductType entityFound) {

    OrderProductType entity = (entityFound != null) ?
            entityFound :
            new OrderProductType();

    entity.setId( dto.getId() );
    entity.setCode( dto.getCode() );
    entity.setName( dto.getName() );
    entity.setDescription( dto.getDescription() );
    entity.setAction( dto.getAction() );
    return entity;
  }

  public OrderProductTypePojo fromEntityToPojo(OrderProductType entity) {
    OrderProductTypePojo pojo = new OrderProductTypePojo();
    pojo.setId( entity.getId() );
    pojo.setCode( entity.getCode() );
    pojo.setName( entity.getName() );
    pojo.setDescription( entity.getDescription() );
    pojo.setAction( entity.getAction() );
    pojo.setActive(entity.getActive());
    pojo.setCreatedDate(entity.getCreatedDate());
    pojo.setCreatedBy(entity.getCreatedBy());
    pojo.setLastModifiedBy(entity.getLastModifiedBy());
    pojo.setLastModifiedDate(entity.getLastModifiedDate());
    return pojo;
  }

  public List<OrderProductTypePojo> fromEntityListToPojoList(List<OrderProductType> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<OrderProductTypePojo> toPagePojo(Page<OrderProductType> page) {
    PagePojo<OrderProductTypePojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
