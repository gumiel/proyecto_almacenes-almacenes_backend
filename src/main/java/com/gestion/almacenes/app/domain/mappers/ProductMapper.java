package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ProductDto;
import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper  extends GenericMapper  {

  public Product fromDtoToEntity(ProductDto dto, Product entityFound) {
    Product entity = (entityFound != null) ?
        entityFound :
        new Product();

    entity.setCode( dto.getCode() );
    entity.setName( dto.getName() );
    entity.setDescription( dto.getDescription() );
    entity.setApproximateUnitPrice( dto.getApproximateUnitPrice() );
    return entity;
  }

  public ProductPojo fromEntityToPojo(Product entity) {
    ProductPojo pojo = new ProductPojo();
    pojo.setId( entity.getId() );
    pojo.setCode( entity.getCode() );
    pojo.setName( entity.getName() );
    pojo.setDescription( entity.getDescription() );
    pojo.setApproximateUnitPrice( entity.getApproximateUnitPrice() );
    pojo.setActive(entity.getActive());
    pojo.setCreatedDate(entity.getCreatedDate());
    pojo.setCreatedBy(entity.getCreatedBy());
    pojo.setLastModifiedBy(entity.getLastModifiedBy());
    pojo.setLastModifiedBy(entity.getLastModifiedBy());
    return pojo;
  }

  public List<ProductPojo> fromEntityListToPojoList(List<Product> entityList){
    return  entityList.stream()
        .map(this::fromEntityToPojo)
        .collect(Collectors.toList());
  }

  public PagePojo<ProductPojo> toPagePojo(Page<Product> page) {
    PagePojo<ProductPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
