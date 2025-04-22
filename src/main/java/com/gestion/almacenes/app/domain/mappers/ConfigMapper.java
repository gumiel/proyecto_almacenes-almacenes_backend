package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.app.presentation.pojos.AuditablePojo;
import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ConfigDto;
import com.gestion.almacenes.app.domain.entities.Config;
import com.gestion.almacenes.app.presentation.pojos.ConfigPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfigMapper extends AuditablePojo {

  public Config fromDtoToEntity(ConfigDto dto, Config entityFound) {
    Config entity = (entityFound != null) ?
        entityFound :
        new Config();

    entity.setCode( dto.getCode() );
    entity.setValue( dto.getValue() );
    entity.setDescription( dto.getDescription() );
    return entity;
  }

  public ConfigPojo fromEntityToPojo(Config entity) {
    ConfigPojo pojo = new ConfigPojo();
    pojo.setId( entity.getId() );
    pojo.setCode( entity.getCode() );
    pojo.setValue( entity.getValue() );
    pojo.setDescription( entity.getDescription() );
    return pojo;
  }

  public List<ConfigPojo> fromEntityListToPojoList(List<Config> entityList){
    return  entityList.stream()
        .map(this::fromEntityToPojo)
        .collect(Collectors.toList());
  }

  public PagePojo<ConfigPojo> toPagePojo(Page<Config> page) {
    PagePojo<ConfigPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
