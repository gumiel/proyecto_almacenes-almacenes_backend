package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.StorehouseType;
import com.gestion.almacenes.app.domain.mappers.StorehouseTypeMapper;
import com.gestion.almacenes.app.domain.services.StorehouseTypeService;
import com.gestion.almacenes.app.persistence.repositories.StorehouseTypeRepository;
import com.gestion.almacenes.app.presentation.dtos.StorehouseTypeDto;
import com.gestion.almacenes.app.presentation.filters.StorehouseTypeFilter;
import com.gestion.almacenes.app.presentation.pojos.StorehouseTypePojo;
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
public class StorehouseTypeServiceImpl implements
    StorehouseTypeService {

  private final StorehouseTypeRepository storehouseTypeRepository;
  private final StorehouseTypeMapper storehouseTypeMapper;

  @Override
  public List<StorehouseTypePojo> getAll() {
    return storehouseTypeMapper.fromEntityListToPojoList(
            storehouseTypeRepository.findAllByActiveIsTrue()
    );
  }

  @Override
  public StorehouseTypePojo create(StorehouseTypeDto storehouseTypedto) {

    if (storehouseTypeRepository.existsByCodeAndActiveIsTrue(storehouseTypedto.getCode())) {
      errorDuplicateInFieldCode(StorehouseTypeDto.class, "code", storehouseTypedto.getCode() );
    }

    StorehouseType storehouseType = storehouseTypeMapper.fromDtoToEntity(storehouseTypedto, null);

    return storehouseTypeMapper.fromEntityToPojo(
            storehouseTypeRepository.save(storehouseType)
    );
  }

  @Override
  public StorehouseTypePojo update(Integer id, StorehouseTypeDto storehouseTypedto) {
    StorehouseType storehouseTypeFound = this.findStorehouseTypeById(id);
    if (storehouseTypeRepository.existsByCodeAndIdNotAndActiveIsTrue(storehouseTypedto.getCode(),
        storehouseTypeFound.getId())) {
      errorDuplicateInFieldCode(StorehouseTypeDto.class, "code", storehouseTypedto.getCode());
    }
    StorehouseType storehouseType = storehouseTypeMapper.fromDtoToEntity(storehouseTypedto,
        storehouseTypeFound);
    //storehouseType.setId(id);
    return storehouseTypeMapper.fromEntityToPojo(
            storehouseTypeRepository.save(storehouseType)
    );
  }

  @Override
  public StorehouseTypePojo getById(Integer id) {
    return storehouseTypeMapper.fromEntityToPojo(
            this.findStorehouseTypeById(id)
    );
  }

  @Override
  public StorehouseTypePojo getByCode(String code) {
    if(storehouseTypeRepository.countByCodeAndActiveTrue(code)>1)
      errorProcess("Existe más de 1 resultado");

    StorehouseType storehouseType = storehouseTypeRepository.findByCodeAndActiveTrue(code).orElseThrow(
            errorEntityNotFound(StorehouseType.class, "code", code)
    );
    return storehouseTypeMapper.fromEntityToPojo(storehouseType);
  }

  @Override
  public void delete(Integer id) {
    StorehouseType storehouseType = this.findStorehouseTypeById(id);
    storehouseTypeRepository.delete(storehouseType);
  }

  @Override
  public void disabled(Integer id) {
    StorehouseType storehouseType = this.findStorehouseTypeById(id);
    if (Boolean.TRUE.equals(storehouseType.getActive())) {
      storehouseType.setActive(false);
      storehouseTypeRepository.save(storehouseType);
    } else {
      errorAlreadyDeleted(StorehouseType.class, storehouseType.getId());
    }
  }

  @Override
  public PagePojo<StorehouseTypePojo> pageable(Integer pageNumber, Integer pageSize,
                                               String sortField, String sortOrder, StorehouseTypeFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<StorehouseType> storehouseTypePage = storehouseTypeRepository.pageable(filter, pageable);

    return storehouseTypeMapper.toPagePojo(storehouseTypePage);
  }

  private StorehouseType findStorehouseTypeById(Integer id) {
    return storehouseTypeRepository.findByIdAndActiveIsTrue(id).orElseThrow(
        errorEntityNotFound(StorehouseType.class, id)
    );
  }

}
