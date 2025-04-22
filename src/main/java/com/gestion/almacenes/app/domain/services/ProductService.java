package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ProductDto;
import com.gestion.almacenes.app.presentation.filters.ProductFilter;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import com.gestion.almacenes.app.persistence.projections.ProductProjection;

import java.util.List;


public interface ProductService {

  List<ProductPojo> getAll();
  ProductPojo create(ProductDto dto);
  ProductPojo update(Integer id, ProductDto dto);
  ProductPojo getById(Integer id);
  ProductPojo getByCode(String code);
  void delete(Integer id);
  void disabled(Integer id);
  PagePojo<ProductPojo> pageable(Integer page, Integer size, String sortField, String sortOrder,
                                 ProductFilter filter);
  PagePojo<ProductProjection> search(int page, int size, String sortField, String sortOrder, String term);



}
