package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.persistence.projections.OrderProductDetailHistoryProjection;

import java.util.List;

public interface OrderProductDetailHistoryService {

  List<OrderProductDetailHistoryProjection> getAllByStockId(Integer id);

  PagePojo<OrderProductDetailHistoryProjection> pageableByStockId(int page, int size, String sortField, String sortOrder, Integer stockId);

}
