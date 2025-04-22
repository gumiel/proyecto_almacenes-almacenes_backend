package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ConfigurationParameterDto;
import com.gestion.almacenes.app.presentation.filters.ConfigurationParameterFilter;
import com.gestion.almacenes.app.presentation.pojos.ConfigurationParameterPojo;

import java.util.List;

public interface ConfigurationParameterService {

  List<ConfigurationParameterPojo> getAll();

  ConfigurationParameterPojo create(ConfigurationParameterDto dto);

  ConfigurationParameterPojo update(Integer id, ConfigurationParameterDto dto);

  ConfigurationParameterPojo getById(Integer id);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<ConfigurationParameterPojo> pageable(Integer page, Integer size, String sortField, String sortOrder,
      ConfigurationParameterFilter filter);

}
