package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductTypeDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductTypeFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductTypePojo;

import java.util.List;


public interface OrderProductTypeService {

  List<OrderProductTypePojo> getAll();

  OrderProductTypePojo create(OrderProductTypeDto dto);

  OrderProductTypePojo update(Integer id, OrderProductTypeDto dto);

  OrderProductTypePojo getById(Integer id);

  OrderProductTypePojo getByCode(String code);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<OrderProductTypePojo> pageable(Integer page, Integer size, String sortField,
                                          String sortOrder, OrderProductTypeFilter filter);
}
