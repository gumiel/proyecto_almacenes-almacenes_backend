package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StorehouseTypeDto;
import com.gestion.almacenes.app.presentation.filters.StorehouseTypeFilter;
import com.gestion.almacenes.app.presentation.pojos.StorehouseTypePojo;

import java.util.List;


public interface StorehouseTypeService {

  List<StorehouseTypePojo> getAll();

  StorehouseTypePojo create(StorehouseTypeDto dto);

  StorehouseTypePojo update(Integer id, StorehouseTypeDto dto);

  StorehouseTypePojo getById(Integer id);

  StorehouseTypePojo getByCode(String code);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<StorehouseTypePojo> pageable(Integer page, Integer size, String sortField,
      String sortOrder, StorehouseTypeFilter filter);
}
