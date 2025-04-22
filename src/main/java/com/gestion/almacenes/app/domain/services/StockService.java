package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.ReportPojo;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StockDto;
import com.gestion.almacenes.app.presentation.filters.StockFilter;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import com.gestion.almacenes.app.presentation.pojos.StockPojo;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public interface StockService {

  List<StockPojo> getAll();

  StockPojo create(StockDto dto);

  StockPojo update(Integer id, StockDto dto);

  StockPojo getById(Integer id);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<StockPojo> search(Integer page, Integer size, String sortField,
                             String sortOrder, StockFilter filter);

  StockPojo getStockByStorehouseIdAndProductId(Integer storehouseId, Integer productId);

  ReportPojo reportByStorehouse(Integer storehouseId) throws JRException, SQLException, IOException;

  PagePojo<ProductPojo> getProductByStorehouseId(Integer page, Integer size, String sortField,
                                                 String sortOrder, Integer storehouseId, String term);
}
