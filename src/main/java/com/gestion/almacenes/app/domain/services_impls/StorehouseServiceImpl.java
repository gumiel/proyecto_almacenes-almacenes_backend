package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.Storehouse;
import com.gestion.almacenes.app.domain.entities.StorehouseType;
import com.gestion.almacenes.app.domain.mappers.StoreHouseMapper;
import com.gestion.almacenes.app.domain.services.StorehouseService;
import com.gestion.almacenes.app.persistence.repositories.StorehouseRepository;
import com.gestion.almacenes.app.persistence.repositories.StorehouseTypeRepository;
import com.gestion.almacenes.app.presentation.dtos.StorehouseDto;
import com.gestion.almacenes.app.presentation.filters.StorehouseFilter;
import com.gestion.almacenes.app.presentation.pojos.StorehousePojo;
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
public class StorehouseServiceImpl implements
    StorehouseService {


  private final StorehouseRepository storehouseRepository;
  private final StorehouseTypeRepository storehouseTypeRepository;
  private final StoreHouseMapper storeHouseMapper;

  @Override
  public List<StorehousePojo> getAll() {
    return storeHouseMapper.fromEntityListToPojoList(storehouseRepository.findAll());
  }

  @Override
  public StorehousePojo create(StorehouseDto storeHousedto) {

    if (storehouseRepository.existsByCodeAndActiveIsTrue(storeHousedto.getCode())) {
      errorDuplicateInFieldCode(StorehouseDto.class, "code", storeHousedto.getCode());

    }

    StorehouseType storehouseType = this.findStorehouseTypeById(
        storeHousedto.getStorehouseTypeId());

    Storehouse storehouse = storeHouseMapper.fromDto(storeHousedto, null);
    storehouse.setStorehouseType(storehouseType);

    return storeHouseMapper.fromEntityToPojo(storehouseRepository.save(storehouse));
  }

  private StorehouseType findStorehouseTypeById(Integer storehouseTypeId) {
    return storehouseTypeRepository.findByIdAndActiveIsTrue(storehouseTypeId).orElseThrow(
        errorEntityNotFound(StorehouseType.class, storehouseTypeId)
    );
  }

  @Override
  public StorehousePojo update(Integer id, StorehouseDto storeHousedto) {
    Storehouse storehouseFound = this.findStoreHouseById(id);
    StorehouseType storehouseType = this.findStorehouseTypeById(
            storeHousedto.getStorehouseTypeId());

    if (storehouseRepository.existsByCodeAndIdNotAndActiveIsTrue(storeHousedto.getCode(),
        storehouseFound.getId())) {
      errorDuplicateInFieldCode(StorehouseDto.class, "code", storeHousedto.getCode());
    }

    storehouseFound = storeHouseMapper.fromDto(storeHousedto, storehouseFound);
    storehouseFound.setStorehouseType(storehouseType);

    return storeHouseMapper.fromEntityToPojo(storehouseRepository.save(storehouseFound));
  }

  @Override
  public StorehousePojo getById(Integer id) {
    return storeHouseMapper.fromEntityToPojo(
        this.findStoreHouseById(id)
    );
  }

  @Override
  public StorehousePojo getByCode(String code) {
    if(storehouseRepository.countByCodeAndActiveTrue(code)>1)
      errorProcess("Existe más de 1 resultado");

    return storeHouseMapper.fromEntityToPojo(
        storehouseRepository.findByCodeAndActiveTrue(code).orElseThrow(
            errorEntityNotFound(Storehouse.class, "code", code)
        )
    );
  }

  @Override
  public void delete(Integer id) {
    Storehouse storeHouse = this.findStoreHouseById(id);
    storehouseRepository.delete(storeHouse);
  }

  @Override
  public void disabled(Integer id) {
    Storehouse storeHouse = this.findStoreHouseById(id);
    if (Boolean.TRUE.equals(storeHouse.getActive())) {
      storeHouse.setActive(false);
      storehouseRepository.save(storeHouse);
    } else {
      errorAlreadyDeleted(Storehouse.class, storeHouse.getId());
    }
  }

  @Override
  public PagePojo<StorehousePojo> pageable(Integer pageNumber, Integer pageSize, String sortField,
      String sortOrder, StorehouseFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<Storehouse> storeHousePage = storehouseRepository.pageable(filter, pageable);

    return storeHouseMapper.toPagePojo(storeHousePage);

  }

  private Storehouse findStoreHouseById(Integer id) {
    return storehouseRepository.findByIdAndActiveIsTrue(id).orElseThrow(
        errorEntityNotFound(Storehouse.class, id)
    );
  }




}
