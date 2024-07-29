package com.gestion.almacenes.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.dtos.ConfigDto;
import com.gestion.almacenes.entities.Config;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ConfigMapper {

  public Config fromDto(ConfigDto dto, Config configFound) {
    Config config = new Config();
    if (configFound != null) {
      config = configFound;
    }
    config.setCode(dto.getCode());
    config.setValue(dto.getValue());

    return config;
  }

  public PagePojo<Config> fromEntity(Page<Config> page) {
    PagePojo<Config> dto = new PagePojo<>();
    dto.setContent(page.getContent());
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }
}
