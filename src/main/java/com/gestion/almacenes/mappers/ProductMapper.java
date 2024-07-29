package com.gestion.almacenes.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.dtos.ProductDto;
import com.gestion.almacenes.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public Product fromDto(ProductDto dto, Product productFound) {
    Product product = new Product();
    if (productFound != null) {
      product = productFound;
    }
    product.setCode(dto.getCode());
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());

    return product;
  }

  public PagePojo<Product> fromEntity(Page<Product> page) {
    PagePojo<Product> dto = new PagePojo<>();
    dto.setContent(page.getContent());
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }
}
