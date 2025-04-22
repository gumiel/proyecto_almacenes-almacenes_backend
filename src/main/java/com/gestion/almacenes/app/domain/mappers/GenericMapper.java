package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import org.springframework.data.domain.Page;

public class GenericMapper {
  /*public PagePojo<T> fromEntity(Page<T> page) {
    PagePojo<T> dto = new PagePojo<>();
    dto.setContent(page.getContent());
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }*/
  public <T> PagePojo<T> fromEntity(Page<T> page) {
    PagePojo<T> dto = new PagePojo<>();
    dto.setContent(page.getContent());
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
