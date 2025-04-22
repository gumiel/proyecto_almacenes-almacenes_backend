package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.CatalogProductStorehouseDto;
import com.gestion.almacenes.app.presentation.filters.CatalogProductStorehouseFilter;
import com.gestion.almacenes.app.presentation.pojos.CatalogProductStorehousePojo;

import java.util.List;


public interface CatalogProductStorehouseService {

  List<CatalogProductStorehousePojo> getAll();

  CatalogProductStorehousePojo create(CatalogProductStorehouseDto dto);

  CatalogProductStorehousePojo update(Integer id, CatalogProductStorehouseDto dto);

  CatalogProductStorehousePojo getById(Integer id);

  void delete(Integer id);

  void disabled(Integer id);

  void deleteByProductAndStorehouse(CatalogProductStorehouseDto dto);

  PagePojo<CatalogProductStorehousePojo> pageable(Integer page, Integer size,
                                                  String sortField, String sortOrder, CatalogProductStorehouseFilter filter);

  void addAllProductsToStorehouse(Integer storehouseId);
}
