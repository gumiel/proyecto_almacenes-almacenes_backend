package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.domain.entities.Stock;
import com.gestion.almacenes.app.domain.entities.Storehouse;
import com.gestion.almacenes.app.domain.mappers.ProductMapper;
import com.gestion.almacenes.app.domain.mappers.StockMapper;
import com.gestion.almacenes.app.domain.services.StockService;
import com.gestion.almacenes.app.persistence.repositories.ProductRepository;
import com.gestion.almacenes.app.persistence.repositories.StockRepository;
import com.gestion.almacenes.app.persistence.repositories.StorehouseRepository;
import com.gestion.almacenes.app.presentation.dtos.StockDto;
import com.gestion.almacenes.app.presentation.filters.StockFilter;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import com.gestion.almacenes.app.presentation.pojos.StockPojo;
import com.gestion.almacenes.commons.ReportPojo;
import com.gestion.almacenes.commons.util.PagePojo;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;
@Service
@AllArgsConstructor
public class StockServiceImpl implements
    StockService {

  private final StockRepository stockRepository;
  private final StockMapper stockMapper;
  private final StorehouseRepository storeHouseRepository;
  private final ProductRepository productRepository;
  private final DataSource dataSource;
  private final ProductMapper productMapper;


  @Override
  public List<StockPojo> getAll() {
    return stockMapper.fromEntityListToPojoList(
            stockRepository.findAllByActiveIsTrue()
    );
  }

  @Override
  public StockPojo create(StockDto stockdto) {

    if (stockRepository.existsByStorehouse_IdAndProduct_IdAndActiveTrue(stockdto.getStorehouseId(),
        stockdto.getProductId())) {
      errorProcess("Ya existe el Stock que desea ingresar.");
    }

    Storehouse storehouse = this.findStorehouseById(stockdto.getStorehouseId());
    Product product = this.findProductById(stockdto.getProductId());
    Stock stock = stockRepository.save(
            Stock.builder().storehouse(storehouse).product(product)
                    .quantityInStock(stockdto.getQuantityInStock())
                    .minimumStock(stockdto.getMinimumStock()).maximumStock(stockdto.getMaximumStock())
                    .stockAlert(stockdto.getStockAlert()).unitPrice(stockdto.getUnitPrice())
                    .validExpirationDate(stockdto.getValidExpirationDate())
                    .build()
    );
    return stockMapper.fromEntityToPojo(
            stock
    );
  }


  @Override
  public StockPojo update(Integer id, StockDto stockdto) {
    Stock stockFound = this.findStockById(id);
    Storehouse storehouse = this.findStorehouseById(stockdto.getStorehouseId());
    Product product = this.findProductById(stockdto.getProductId());
    Stock stock = stockRepository.save(
            Stock.builder().storehouse(storehouse).product(product)
                    .quantityInStock(stockdto.getQuantityInStock())
                    .minimumStock(stockdto.getMinimumStock()).maximumStock(stockdto.getMaximumStock())
                    .stockAlert(stockdto.getStockAlert()).unitPrice(stockdto.getUnitPrice()).build()
    );
    return stockMapper.fromEntityToPojo(stock);
  }

  @Override
  public StockPojo getById(Integer id) {
    return stockMapper.fromEntityToPojo(this.findStockById(id));
  }

  @Override
  public void delete(Integer id) {
    stockRepository.delete(
        this.findStockById(id)
    );
  }

  @Override
  public void disabled(Integer id) {
    Stock stock = this.findStockById(id);
    if (Boolean.TRUE.equals(stock.getActive())) {
      stock.setActive(false);
      stockRepository.save(stock);
    } else {
      errorAlreadyDeleted(Stock.class, stock.getId());
    }
  }

  @Override
  public PagePojo<StockPojo> search(Integer pageNumber, Integer pageSize, String sortField,
      String sortOrder, StockFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Stock> stockPage = stockRepository.pageable(filter,  pageable);

    return stockMapper.toPagePojo(stockPage);
  }

  @Override
  public StockPojo getStockByStorehouseIdAndProductId(Integer storehouseId, Integer productId) {
    Stock stock = stockRepository.findByStorehouse_IdAndProduct_IdAndActiveTrue(storehouseId, productId).orElseThrow(

    );
    return stockMapper.fromEntityToPojo(stock);
  }

  private Stock findStockById(Integer id) {

    return stockRepository.findByIdAndActiveIsTrue(id).orElseThrow(
            errorEntityNotFound(Stock.class, id)
    );

  }

  private Product findProductById(Integer productId) {

    return productRepository.findByIdAndActiveIsTrue(productId).orElseThrow(
            errorEntityNotFound(Product.class, productId)
    );

  }

  private Storehouse findStorehouseById(Integer storehouseId) {
    return storeHouseRepository.findByIdAndActiveIsTrue(storehouseId).orElseThrow(
        errorEntityNotFound(Storehouse.class, storehouseId)
    );
  }


  @Override
  public ReportPojo reportByStorehouse(Integer storehouseId) throws SQLException {


    try (Connection connection = dataSource.getConnection()) {


      Map<String, Object> params = new HashMap<>();
      params.put("storehouseId", 1);

      JasperReport jasperReport = JasperCompileManager.compileReport(
              ResourceUtils.getFile("classpath:templates/report/Report_stock.jrxml").getAbsolutePath());

      JasperPrint report = JasperFillManager.fillReport(
              jasperReport,
              params,
              dataSource.getConnection());
      //JasperExportManager.exportReportToPdfStream(report, stream);

    } catch (JRException e) {
        throw new RuntimeException(e);
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }


      return null;
  }

  @Override
  public PagePojo<ProductPojo> getProductByStorehouseId(Integer pageNumber, Integer pageSize, String sortField,
                                          String sortOrder, Integer storehouseId, String term) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Product>products =  stockRepository.findByStorehouseId(storehouseId, term, pageable);
    return productMapper.toPagePojo(products);
  }
}
