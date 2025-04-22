package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.UnitMeasurementDto;
import com.gestion.almacenes.app.presentation.filters.UnitMeasurementFilter;
import com.gestion.almacenes.app.presentation.pojos.UnitMeasurementPojo;

import java.util.List;


public interface UnitMeasurementService {

  List<UnitMeasurementPojo> getAll();

  UnitMeasurementPojo create(UnitMeasurementDto dto);

  UnitMeasurementPojo update(Integer id, UnitMeasurementDto dto);

  UnitMeasurementPojo getById(Integer id);

  UnitMeasurementPojo getByCode(String code);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<UnitMeasurementPojo> pageable(Integer page, Integer size, String sortField,
                                         String sortOrder, UnitMeasurementFilter filter);
}
