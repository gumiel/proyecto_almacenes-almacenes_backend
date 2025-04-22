package com.gestion.almacenes.app.domain.services_impls;

//import com.gestion.almacenes.commons.util.GenericMapper;

import com.gestion.almacenes.app.domain.entities.*;
import com.gestion.almacenes.app.domain.mappers.ProductMapper;
import com.gestion.almacenes.app.persistence.projections.ProductProjection;
import com.gestion.almacenes.app.domain.services.ProductService;
import com.gestion.almacenes.app.persistence.repositories.CatalogProductStorehouseRepository;
import com.gestion.almacenes.app.persistence.repositories.ProductRepository;
import com.gestion.almacenes.app.persistence.repositories.StorehouseRepository;
import com.gestion.almacenes.app.persistence.repositories.UnitMeasurementRepository;
import com.gestion.almacenes.app.presentation.dtos.ProductDto;
import com.gestion.almacenes.app.presentation.filters.ProductFilter;
import com.gestion.almacenes.app.presentation.pojos.ProductPojo;
import com.gestion.almacenes.commons.util.PagePojo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Transactional
@Service
@AllArgsConstructor
public class ProductServiceImpl implements
    ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final UnitMeasurementRepository unitMeasurementRepository;
  private final StorehouseRepository storehouseRepository;
  private final CatalogProductStorehouseRepository catalogProductStorehouseRepository;

  @Override
  public List<ProductPojo> getAll() {
    return productMapper.fromEntityListToPojoList(
        productRepository.findAll()
    );
  }

  @Override
  public ProductPojo create(ProductDto productdto) {

    if (productRepository.existsByCodeAndActiveIsTrue(productdto.getCode())) {
      errorDuplicateInFieldCode(ProductDto.class, "code", productdto.getCode());
    }

    Product product = productMapper.fromDtoToEntity(productdto, null);
    product.setUnitMeasurement(
        this.findUnitMeasurementById(
            productdto.getUnitMeasurementId())
    );

    Product productNew = productRepository.save(product);



    if(productdto.getSelectAllStorehouse()){

      for(Storehouse storehouse: storehouseRepository.findByActiveTrue()){
        this.createCatalogProductStorehouse(productNew, storehouse);
      }

    }else{
      if(productdto.getStorehouseIds()!=null) {
        for (Integer storehouseId : productdto.getStorehouseIds()) {
          Storehouse storehouse = storehouseRepository.findByIdAndActiveIsTrue(storehouseId).orElseThrow(
                  errorEntityNotFound(Storehouse.class, storehouseId)
          );
          this.createCatalogProductStorehouse(productNew, storehouse);
        }
      }else{
        //errorProcess("La lista de almacenes esta vacía.");
      }
    }

    return productMapper.fromEntityToPojo(productNew);
  }


  @Override
  public ProductPojo update(Integer id, ProductDto productdto) {
    Product productFound = this.findProductById(id);
    if (productRepository.existsByCodeAndIdNotAndActiveIsTrue(productdto.getCode(),
        productFound.getId())) {
      errorDuplicateInFieldCode(ProductDto.class, "code", productdto.getCode());
    }
    Product product = productMapper.fromDtoToEntity(productdto, productFound);
    product.setUnitMeasurement(
        this.findUnitMeasurementById(
            productdto.getUnitMeasurementId())
    );
    Product productEdited = productRepository.save(product);



    if(productdto.getSelectAllStorehouse()){

      List<Integer> storehouseIdsNotId = catalogProductStorehouseRepository.findStorehouseIdNotInByProductId(productEdited.getId());
      for(Integer storehouseId: storehouseIdsNotId){
        Storehouse storehouse = this.findStorehouseById(storehouseId);
        this.createCatalogProductStorehouse(productEdited, storehouse);
      }

    }else{

      if(productdto.getStorehouseIds()!=null){


        List<Integer> storehouseIds = catalogProductStorehouseRepository.findStorehouseIdByProductId(productEdited.getId());

        // Crear una copia de las listas para no modificar las originales
        List<Integer> toDeleteStorehouse  = new ArrayList<>(storehouseIds);
        List<Integer> toCreateStorehouse  = new ArrayList<>(productdto.getStorehouseIds());

        toDeleteStorehouse.removeAll(productdto.getStorehouseIds());

        for(Integer storehouseId: toDeleteStorehouse){
          Storehouse storehouse = this.findStorehouseById(storehouseId);
          catalogProductStorehouseRepository.deleteByStorehouseAndProduct(storehouse, productEdited);
        }

        toCreateStorehouse.removeAll(storehouseIds);

        for(Integer storehouseId: toCreateStorehouse){
          Storehouse storehouse = this.findStorehouseById(storehouseId);
          this.createCatalogProductStorehouse(productEdited, storehouse);
        }

      }

    }

    return productMapper.fromEntityToPojo(productEdited);

  }



  @Override
  public ProductPojo getById(Integer id) {
    return productMapper.fromEntityToPojo(
        this.findProductById(id)
    );
  }

  @Override
  public ProductPojo getByCode(String code) {
    if(productRepository.countByCodeAndActiveTrue(code)>1)
      errorProcess("Existe más de 1 resultado");
      Product product = productRepository.findByCodeAndActiveTrue(code).orElseThrow(
          errorEntityNotFound(Product.class, "code", code)
      );

    return productMapper.fromEntityToPojo(product);
  }

  @Override
  public void delete(Integer id) {
    Product product = this.findProductById(id);
    productRepository.delete(product);
  }

  @Override
  public void disabled(Integer id) {
    Product product = this.findProductById(id);
    if (Boolean.TRUE.equals(product.getActive())) {
      product.setActive(false);
      productRepository.save(product);
    } else {
      errorAlreadyDeleted(OrderProductType.class, product.getId());
    }
  }

  @Override
  public PagePojo<ProductPojo> pageable(Integer pageNumber, Integer pageSize, String sortField,
      String sortOrder, ProductFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Product> productPage = productRepository.pageable(filter, pageable);

    return productMapper.toPagePojo(productPage);
  }

  @Override
  public PagePojo<ProductProjection> search(int pageNumber, int pageSize, String sortField, String sortOrder,
      String term) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<ProductProjection> productPage = productRepository.search(term, pageable);

    return productMapper.fromEntity(productPage);
  }

  private Product findProductById(Integer id) {
    return productRepository.findById(id).orElseThrow(
        errorEntityNotFound(Product.class, id)
    );
  }

  private UnitMeasurement findUnitMeasurementById(Integer unitMeasurementId) {
    return unitMeasurementRepository.findByIdAndActiveIsTrue(unitMeasurementId).orElseThrow(
        errorEntityNotFound(UnitMeasurement.class, unitMeasurementId)
    );
  }

  private void createCatalogProductStorehouse(Product productEdited, Storehouse storehouse) {
    CatalogProductStorehouse catalogProductStorehouse = new CatalogProductStorehouse();
    catalogProductStorehouse.setProduct(productEdited);
    catalogProductStorehouse.setStorehouse(storehouse);
    catalogProductStorehouseRepository.save(catalogProductStorehouse);
  }

  private Storehouse findStorehouseById(Integer storehouseId) {
    return storehouseRepository.findById(storehouseId).orElseThrow(
            errorEntityNotFound(Storehouse.class, storehouseId)
    );
  }

}
