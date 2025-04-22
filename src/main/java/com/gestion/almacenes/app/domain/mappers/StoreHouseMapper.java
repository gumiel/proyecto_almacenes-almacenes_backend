package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.StorehouseDto;
import com.gestion.almacenes.app.domain.entities.Storehouse;
import com.gestion.almacenes.app.presentation.pojos.StorehousePojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreHouseMapper  extends GenericMapper {

  public Storehouse fromDto(StorehouseDto dto, Storehouse storeHouseFound) {
    Storehouse storeHouse = new Storehouse();
    if (storeHouseFound != null) {
      storeHouse = storeHouseFound;
    }
    storeHouse.setCode(dto.getCode());
    storeHouse.setName(dto.getName());
    storeHouse.setDescription(dto.getDescription());

    return storeHouse;
  }

   public StorehousePojo fromEntityToPojo(Storehouse entity) {
      StorehousePojo pojo = new StorehousePojo();
     pojo.setId(entity.getId());
     pojo.setCode(entity.getCode());
     pojo.setName(entity.getName());
     pojo.setDescription(entity.getDescription());
     pojo.setActive(entity.getActive());
     pojo.setCreatedBy(entity.getCreatedBy());
     pojo.setCreatedDate(entity.getCreatedDate());
     pojo.setLastModifiedBy(entity.getLastModifiedBy());
     pojo.setLastModifiedDate(entity.getLastModifiedDate());
     if((entity.getStorehouseType()!=null)){
         pojo.setStorehouseTypeId(entity.getStorehouseType().getId());
         pojo.setStorehouseTypeCode(entity.getStorehouseType().getCode());
         pojo.setStorehouseTypeName(entity.getStorehouseType().getName());
     }
     return pojo;
   }

   public List<StorehousePojo> fromEntityListToPojoList(List<Storehouse> entityList){
      return  entityList.stream()
      .map(this::fromEntityToPojo)
      .collect(Collectors.toList());
   }

   public PagePojo<StorehousePojo> toPagePojo(Page<Storehouse> page) {
      PagePojo<StorehousePojo> dto = new PagePojo<>();
      dto.setContent(fromEntityListToPojoList(page.getContent()));
      dto.setLast(page.isLast());
      dto.setPageNumber(page.getNumber());
      dto.setPageSize(page.getSize());
      dto.setTotalPages(page.getTotalPages());
      dto.setTotalElements(page.getTotalElements());
      return dto;
   }

}
