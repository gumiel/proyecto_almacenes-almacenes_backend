package com.gestion.almacenes.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.dtos.ConfigDto;
import com.gestion.almacenes.entities.Config;
import java.util.List;


public interface ConfigService {

  List<Config> getAll();

  Config create(ConfigDto dto);

  Config update(Integer id, ConfigDto dto);

  Config getById(Integer id);

  void delete(Integer id);

  List<Config> getFiltered(String code, String name);

  PagePojo<Config> getByPageAndFilters(Integer page, Integer size, String sortField,
      String sortOrder, String code, String name);

  String getValueByCode(String code);
}
