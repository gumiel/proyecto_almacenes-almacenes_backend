package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.UnitMeasurement;
import com.gestion.almacenes.app.domain.mappers.UnitMeasurementMapper;
import com.gestion.almacenes.app.domain.services.UnitMeasurementService;
import com.gestion.almacenes.app.persistence.repositories.UnitMeasurementRepository;
import com.gestion.almacenes.app.presentation.dtos.UnitMeasurementDto;
import com.gestion.almacenes.app.presentation.filters.UnitMeasurementFilter;
import com.gestion.almacenes.app.presentation.pojos.UnitMeasurementPojo;
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
public class UnitMeasurementServiceImpl implements
    UnitMeasurementService {

  private final UnitMeasurementRepository unitMeasurementRepository;
  private final UnitMeasurementMapper unitMeasurementMapper;

  @Override
  public List<UnitMeasurementPojo> getAll() {
    return unitMeasurementMapper.fromEntityListToPojoList(
            unitMeasurementRepository.findAllByActiveIsTrue()
    );
  }

  @Override
  public UnitMeasurementPojo create(UnitMeasurementDto unitMeasurementdto) {

    if (unitMeasurementRepository.existsByCodeAndActiveIsTrue(
        unitMeasurementdto.getCode())
    ) {
      errorDuplicateInFieldCode(UnitMeasurementDto.class, "code", unitMeasurementdto.getCode());
    }

    UnitMeasurement unitMeasurement = unitMeasurementMapper.fromDtoToEntity(unitMeasurementdto, null);

    return unitMeasurementMapper.fromEntityToPojo(
            unitMeasurementRepository.save(unitMeasurement)
    );

  }

  @Override
  public UnitMeasurementPojo update(Integer id, UnitMeasurementDto unitMeasurementdto) {
    UnitMeasurement unitMeasurementFound = this.findUnitMeasurementById(id);
    if (unitMeasurementRepository.existsByCodeAndIdNotAndActiveIsTrue(
        unitMeasurementdto.getCode(), unitMeasurementFound.getId())) {
      errorDuplicateInFieldCode(UnitMeasurementDto.class, "code", unitMeasurementdto.getCode());
    }
    UnitMeasurement unitMeasurement = unitMeasurementMapper.fromDtoToEntity(unitMeasurementdto, unitMeasurementFound);

    return unitMeasurementMapper.fromEntityToPojo(
            unitMeasurementRepository.save(unitMeasurement)
    );
  }

  @Override
  public UnitMeasurementPojo getById(Integer id) {
    return unitMeasurementMapper.fromEntityToPojo(
            this.findUnitMeasurementById(id)
    );
  }

  @Override
  public UnitMeasurementPojo getByCode(String code) {
    if(unitMeasurementRepository.countByCodeAndActiveTrue(code)>1)
      errorProcess("Existe más de 1 resultado");

    UnitMeasurement measurement = unitMeasurementRepository.findByCodeAndActiveTrue(code).orElseThrow(
            errorEntityNotFound(UnitMeasurement.class, "code", code)
    );
    return unitMeasurementMapper.fromEntityToPojo(measurement);
  }

  @Override
  public void delete(Integer id) {
    UnitMeasurement unitMeasurement = this.findUnitMeasurementById(id);
    unitMeasurementRepository.delete(unitMeasurement);
  }

  @Override
  public void disabled(Integer id) {
    UnitMeasurement unitMeasurement = this.findUnitMeasurementById(id);
    if (Boolean.TRUE.equals(unitMeasurement.getActive())) {
      unitMeasurement.setActive(false);
      unitMeasurementRepository.save(unitMeasurement);
    } else {
      errorAlreadyDeleted(UnitMeasurement.class, unitMeasurement.getId());
    }
  }

  @Override
  public PagePojo<UnitMeasurementPojo> pageable(Integer pageNumber, Integer pageSize,
                                                String sortField, String sortOrder, UnitMeasurementFilter filter) {

    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    Page<UnitMeasurement> unitMeasurementPage = unitMeasurementRepository.pageable(filter, pageable);

    return unitMeasurementMapper.toPagePojo(unitMeasurementPage);
  }

  private UnitMeasurement findUnitMeasurementById(Integer id) {
    return unitMeasurementRepository.findByIdAndActiveIsTrue(id).orElseThrow(
        errorEntityNotFound(UnitMeasurement.class, id)
    );
  }

}
