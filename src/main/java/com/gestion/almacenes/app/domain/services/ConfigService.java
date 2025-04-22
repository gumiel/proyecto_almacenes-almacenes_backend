package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ConfigDto;
import com.gestion.almacenes.app.presentation.filters.ConfigFilter;
import com.gestion.almacenes.app.presentation.pojos.ConfigPojo;

import java.util.List;


public interface ConfigService {

  List<ConfigPojo> getAll();

  ConfigPojo create(ConfigDto dto);

  ConfigPojo update(Integer id, ConfigDto dto);

  ConfigPojo getById(Integer id);

  ConfigPojo getByCode(String code);

  void delete(Integer id);

  void disabled(Integer id);

  PagePojo<ConfigPojo> pageable(Integer page, Integer size, String sortField,
                                String sortOrder, ConfigFilter filter);

  List<String> lista(String code, String name);
}
