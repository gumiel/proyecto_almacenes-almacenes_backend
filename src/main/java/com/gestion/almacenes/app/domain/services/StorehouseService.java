package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StorehouseDto;
import com.gestion.almacenes.app.presentation.filters.StorehouseFilter;
import com.gestion.almacenes.app.presentation.pojos.StorehousePojo;

import java.util.List;


public interface StorehouseService {

  List<StorehousePojo> getAll();

  StorehousePojo create(StorehouseDto dto);

  StorehousePojo update(Integer id, StorehouseDto dto);

  StorehousePojo getById(Integer id);

  StorehousePojo getByCode(String code);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<StorehousePojo> pageable(Integer page, Integer size, String sortField, String sortOrder,
                                    StorehouseFilter filter);
}
