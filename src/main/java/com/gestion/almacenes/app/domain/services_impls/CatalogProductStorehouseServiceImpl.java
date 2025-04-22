package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.CatalogProductStorehouse;
import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.domain.entities.Storehouse;
import com.gestion.almacenes.app.domain.mappers.CatalogProductStorehouseMapper;
import com.gestion.almacenes.app.domain.services.CatalogProductStorehouseService;
import com.gestion.almacenes.app.persistence.repositories.CatalogProductStorehouseRepository;
import com.gestion.almacenes.app.persistence.repositories.ProductRepository;
import com.gestion.almacenes.app.persistence.repositories.StorehouseProductRepository;
import com.gestion.almacenes.app.persistence.repositories.StorehouseRepository;
import com.gestion.almacenes.app.presentation.dtos.CatalogProductStorehouseDto;
import com.gestion.almacenes.app.presentation.filters.CatalogProductStorehouseFilter;
import com.gestion.almacenes.app.presentation.pojos.CatalogProductStorehousePojo;
import com.gestion.almacenes.commons.util.PagePojo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Service
@AllArgsConstructor
public class CatalogProductStorehouseServiceImpl implements
        CatalogProductStorehouseService {

    private final CatalogProductStorehouseRepository catalogProductStorehouseRepository;
//    private final GenericMapper<CatalogProductStorehouse, CatalogProductStorehouseDto> catalogProductStorehouseMMapper = new GenericMapper<>(
//            CatalogProductStorehouse.class);
    private final CatalogProductStorehouseMapper catalogProductStorehouseMapper;
    private final StorehouseRepository storehouseRepository;
    private final ProductRepository productRepository;
    private final StorehouseProductRepository storehouseProductRepository;

    @Override
    public List<CatalogProductStorehousePojo> getAll() {
        return catalogProductStorehouseMapper.fromEntityListToPojoList(
                catalogProductStorehouseRepository.findAllByActiveIsTrue()
        );
    }

    @Override
    public CatalogProductStorehousePojo create(CatalogProductStorehouseDto dto) {

        if (catalogProductStorehouseRepository.existsByStorehouseId_IdAndProductId_Id(dto.getStorehouseId(),
                dto.getProductId())) {
            errorDuplicate(CatalogProductStorehouse.class, "Almacen",
                    dto.getStorehouseId().toString());
        }

        CatalogProductStorehouse catalogProductStorehouse = new CatalogProductStorehouse();
        catalogProductStorehouse.setStorehouse(this.findStoreHouseById(dto.getStorehouseId()));
        catalogProductStorehouse.setProduct(this.findProductById(dto.getProductId()));
        catalogProductStorehouseRepository.save(catalogProductStorehouse);
        return catalogProductStorehouseMapper.fromEntityToPojo(catalogProductStorehouse);

    }

    @Override
    public CatalogProductStorehousePojo update(Integer id, CatalogProductStorehouseDto catalogProductStorehousedto) {

        CatalogProductStorehouse catalogProductStorehouseFound = this.findCatalogProductStorehouseById(id);

        catalogProductStorehouseFound = catalogProductStorehouseMapper.fromDtoToEntity(catalogProductStorehousedto, catalogProductStorehouseFound);

        return catalogProductStorehouseMapper.fromEntityToPojo(
                catalogProductStorehouseRepository.save(catalogProductStorehouseFound)
        );

    }

    @Override
    public CatalogProductStorehousePojo getById(Integer id) {
        return catalogProductStorehouseMapper.fromEntityToPojo(
                this.findCatalogProductStorehouseById(id)
        );
    }

    @Override
    public void delete(Integer id) {
        CatalogProductStorehouse catalogProductStorehouseFound = this.findCatalogProductStorehouseById(id);
        catalogProductStorehouseRepository.delete(catalogProductStorehouseFound);
    }

    @Override
    public void disabled(Integer id) {
        CatalogProductStorehouse catalogProductStorehouse = this.findCatalogProductStorehouseById(id);
        if (Boolean.TRUE.equals(catalogProductStorehouse.getActive())) {
            catalogProductStorehouse.setActive(false);
            catalogProductStorehouseRepository.save(catalogProductStorehouse);
        } else {
            errorAlreadyDeleted(CatalogProductStorehouse.class, catalogProductStorehouse.getId());
        }
    }

    @Override
    public void deleteByProductAndStorehouse(CatalogProductStorehouseDto dto) {
        CatalogProductStorehouse catalogProductStorehouse = catalogProductStorehouseRepository.findByStorehouseId_IdAndProductId_Id(
                dto.getStorehouseId(), dto.getProductId());
        catalogProductStorehouseRepository.delete(catalogProductStorehouse);
    }

    @Override
    public PagePojo<CatalogProductStorehousePojo> pageable(Integer pageNumber, Integer pageSize, String sortField, String sortOrder, CatalogProductStorehouseFilter filter) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<CatalogProductStorehouse> catalogProductStorehousePage;

        catalogProductStorehousePage = catalogProductStorehouseRepository.pageable(filter, pageable);


        return catalogProductStorehouseMapper.toPagePojo(catalogProductStorehousePage);
    }

    private CatalogProductStorehouse findCatalogProductStorehouseById(Integer id) {
        return catalogProductStorehouseRepository.findByIdAndActiveIsTrue(id).orElseThrow(
                errorEntityNotFound(CatalogProductStorehouse.class, id)
        );
    }

    private Storehouse findStoreHouseById(Integer id) {
        return storehouseRepository.findByIdAndActiveIsTrue(id).orElseThrow(
                errorEntityNotFound(Storehouse.class, id)
        );
    }

    private Product findProductById(Integer id) {
        return productRepository.findByIdAndActiveIsTrue(id).orElseThrow(
                errorEntityNotFound(Product.class, id)
        );
    }

    @Override
    public void addAllProductsToStorehouse(Integer storehouseId) {
        Storehouse storehouse = this.findStoreHouseById(storehouseId);
        catalogProductStorehouseRepository.addAllProductsToStorehouse(storehouse.getId());
    }
}
