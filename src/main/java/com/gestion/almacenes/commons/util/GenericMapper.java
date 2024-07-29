package com.gestion.almacenes.commons.util;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;


public class GenericMapper<I, D> {

  private final ModelMapper modelMapper = new ModelMapper();
  private final Class<I> type;

  public GenericMapper(Class<I> type) {
    this.type = type;
  }

  public I fromDto(D dto) {
    return modelMapper.map(dto, type);
  }

  public PagePojo<I> fromEntity(Page<I> page) {
    PagePojo<I> dto = new PagePojo<>();
    dto.setContent(page.getContent());
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
