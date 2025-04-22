package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDto;
import com.gestion.almacenes.app.domain.entities.OrderProduct;
import com.gestion.almacenes.app.presentation.pojos.OrderProductPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderProductMapper extends GenericMapper {

  public OrderProduct fromDtoToEntity(OrderProductDto dto, OrderProduct entityFound) {

    OrderProduct entity = (entityFound != null) ?
            entityFound :
            new OrderProduct();

    entity.setId( dto.getId() );
    entity.setCode( dto.getCode() );
    entity.setRegistrationDate( dto.getRegistrationDate() );
    entity.setRegistrationTime( dto.getRegistrationTime() );
    entity.setDescription( dto.getDescription() );
    entity.setStatus( dto.getStatus() );

    return entity;
  }

  public OrderProductPojo fromEntityToPojo(OrderProduct entity) {
    OrderProductPojo pojo = new OrderProductPojo();
    pojo.setId( entity.getId() );
    pojo.setCode( entity.getCode() );
    pojo.setRegistrationDate( entity.getRegistrationDate() );
    pojo.setRegistrationTime( entity.getRegistrationTime() );
    pojo.setStorehouseId( entity.getStorehouse().getId() );
    pojo.setDescription( entity.getDescription() );
    if(entity.getOrderProductType()!=null){
      pojo.setOrderProductTypeId( entity.getOrderProductType().getId() );
    }
    pojo.setStatus( entity.getStatus() );
    if(entity.getSupplier()!=null){
      pojo.setSupplierId( entity.getSupplier().getId() );
    }

    return pojo;
  }

  public List<OrderProductPojo> fromEntityListToPojoList(List<OrderProduct> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<OrderProductPojo> toPagePojo(Page<OrderProduct> page) {
    PagePojo<OrderProductPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
