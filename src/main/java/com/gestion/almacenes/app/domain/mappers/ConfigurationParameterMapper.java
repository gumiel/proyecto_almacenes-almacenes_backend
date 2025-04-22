package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.ConfigurationParameterDto;
import com.gestion.almacenes.app.domain.entities.ConfigurationParameter;
import com.gestion.almacenes.app.presentation.pojos.ConfigurationParameterPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfigurationParameterMapper extends GenericMapper {

  public ConfigurationParameter fromDtoToEntity(ConfigurationParameterDto dto, ConfigurationParameter entityFound) {

    ConfigurationParameter entity = (entityFound != null) ?
            entityFound :
            new ConfigurationParameter();

    entity.setCode(dto.getCode());
    entity.setValue(dto.getValue());
    entity.setDescription(dto.getDescription());

    return entity;
  }

  public ConfigurationParameterPojo fromEntityToPojo(ConfigurationParameter entity) {
    ConfigurationParameterPojo pojo = new ConfigurationParameterPojo();
    pojo.setId(entity.getId());
    pojo.setCode(entity.getCode());
    pojo.setValue(entity.getValue());
    pojo.setDescription(entity.getDescription());
    return pojo;
  }

  public List<ConfigurationParameterPojo> fromEntityListToPojoList(List<ConfigurationParameter> travelRequestsList){
    return  travelRequestsList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<ConfigurationParameterPojo> toPagePojo(Page<ConfigurationParameter> page) {
    PagePojo<ConfigurationParameterPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }


}