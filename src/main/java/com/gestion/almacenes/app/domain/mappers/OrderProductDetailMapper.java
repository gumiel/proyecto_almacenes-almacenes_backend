package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDetailDto;
import com.gestion.almacenes.app.domain.entities.OrderProductDetail;
import com.gestion.almacenes.app.presentation.pojos.OrderProductDetailPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderProductDetailMapper extends GenericMapper {

  public OrderProductDetail fromDtoToEntity(OrderProductDetailDto dto, OrderProductDetail entityFound) {

    OrderProductDetail entity = (entityFound != null) ?
            entityFound :
            new OrderProductDetail();

    entity.setId( dto.getId() );
    entity.setQuantity( dto.getQuantity() );
    entity.setCodeProduct( dto.getCodeProduct() );
    entity.setExpirationDateProduct( dto.getExpirationDateProduct() );
    entity.setPrice( dto.getPrice() );
    return entity;
  }

  public OrderProductDetailPojo fromEntityToPojo(OrderProductDetail entity) {
    OrderProductDetailPojo pojo = new OrderProductDetailPojo();
    pojo.setId( entity.getId() );
    pojo.setOrderProductId( entity.getOrderProduct().getId() );
    pojo.setStockId( entity.getStock().getId() );
    pojo.setQuantity( entity.getQuantity() );
    pojo.setCodeProduct( entity.getCodeProduct() );
    pojo.setExpirationDateProduct( entity.getExpirationDateProduct() );
    pojo.setPrice( entity.getPrice() );
    return pojo;
  }

  public List<OrderProductDetailPojo> fromEntityListToPojoList(List<OrderProductDetail> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<OrderProductDetailPojo> toPagePojo(Page<OrderProductDetail> page) {
    PagePojo<OrderProductDetailPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
