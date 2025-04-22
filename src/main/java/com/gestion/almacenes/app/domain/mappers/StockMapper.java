package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StockDto;
import com.gestion.almacenes.app.domain.entities.Stock;
import com.gestion.almacenes.app.presentation.pojos.StockPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper extends GenericMapper {

  public Stock fromDtoToEntity(StockDto dto, Stock entityFound) {

    Stock entity = (entityFound != null) ?
            entityFound :
            new Stock();

    entity.setId( dto.getId() );
    entity.setQuantityInStock( dto.getQuantityInStock() );
    entity.setMinimumStock( dto.getMinimumStock() );
    entity.setMaximumStock( dto.getMaximumStock() );
    entity.setStockAlert( dto.getStockAlert() );
    entity.setValidExpirationDate( dto.getValidExpirationDate() );
    entity.setUnitPrice( dto.getUnitPrice() );
    return entity;
  }

  public StockPojo fromEntityToPojo(Stock entity) {
    StockPojo pojo = new StockPojo();
    pojo.setId( entity.getId() );

    pojo.setQuantityInStock( entity.getQuantityInStock() );
    pojo.setMinimumStock( entity.getMinimumStock() );
    pojo.setMaximumStock( entity.getMaximumStock() );
    pojo.setStockAlert( entity.getStockAlert() );
    pojo.setValidExpirationDate( entity.getValidExpirationDate() );
    pojo.setUnitPrice( entity.getUnitPrice() );
    pojo.setActive(entity.getActive());
    pojo.setCreatedBy(entity.getCreatedBy());
    pojo.setCreatedDate(entity.getCreatedDate());
    pojo.setLastModifiedBy(entity.getLastModifiedBy());
    pojo.setLastModifiedDate(entity.getLastModifiedDate());
    if(entity.getStorehouse()!=null){
      pojo.setStorehouseId( entity.getStorehouse().getId() );
      pojo.setStorehouseName( entity.getStorehouse().getName() );
    }
    if(entity.getProduct()!=null){
      pojo.setProductId( entity.getProduct().getId() );
      pojo.setProductName( entity.getProduct().getName() );
    }
    pojo.setValidExpirationDate(entity.getValidExpirationDate());

    return pojo;
  }

  public List<StockPojo> fromEntityListToPojoList(List<Stock> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<StockPojo> toPagePojo(Page<Stock> page) {
    PagePojo<StockPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
