package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.OrderProductDetailDto;
import com.gestion.almacenes.app.presentation.filters.OrderProductDetailFilter;
import com.gestion.almacenes.app.presentation.pojos.OrderProductDetailPojo;

import java.util.List;


public interface OrderProductDetailService {

  List<OrderProductDetailPojo> getAll();

  OrderProductDetailPojo create(OrderProductDetailDto dto);

  OrderProductDetailPojo update(Integer id, OrderProductDetailDto dto);

  OrderProductDetailPojo getById(Integer id);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<OrderProductDetailPojo> getByPageAndFilters(Integer page, Integer size, String sortField,
      String sortOrder, String code, String name);

    void createList(List<OrderProductDetailDto> orderProductDetailDtos);

  PagePojo<OrderProductDetailPojo> search(int page, int size, String sortField, String sortOrder, OrderProductDetailFilter filter);
}
