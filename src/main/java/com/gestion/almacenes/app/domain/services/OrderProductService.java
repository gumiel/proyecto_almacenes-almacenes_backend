package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductPojo;

import java.util.List;


public interface OrderProductService {

  List<OrderProductPojo> getAll();

  OrderProductPojo create(OrderProductDto dto);

  OrderProductPojo update(Integer id, OrderProductDto dto);

  OrderProductPojo getById(Integer id);

  OrderProductPojo getByCode(String code);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<OrderProductPojo> pageable(Integer page, Integer size, String sortField,
                                      String sortOrder, OrderProductFilter filter);

  OrderProductPojo executeOrderProduct(OrderProductDto dto);
}
