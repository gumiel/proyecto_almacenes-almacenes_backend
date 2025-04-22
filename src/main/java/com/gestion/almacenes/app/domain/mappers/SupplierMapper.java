package com.gestion.almacenes.app.domain.mappers;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.SupplierDto;
import com.gestion.almacenes.app.domain.entities.Supplier;
import com.gestion.almacenes.app.presentation.pojos.SupplierPojo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper extends GenericMapper {

  public Supplier fromDtoToEntity(SupplierDto dto, Supplier entityFound) {

    Supplier entity = (entityFound != null) ?
            entityFound :
            new Supplier();

    entity.setId( dto.getId() );
    entity.setRegisterDate( dto.getRegisterDate() );
    entity.setSupplierCode( dto.getSupplierCode() );
    entity.setSupplierPhoneNumber( dto.getSupplierPhoneNumber() );
    entity.setSupplierCelNumber( dto.getSupplierCelNumber() );
    entity.setCompanyName( dto.getCompanyName() );
    entity.setAddress( dto.getAddress() );
    entity.setCompanyDescription( dto.getCompanyDescription() );
    entity.setEnable( dto.getEnable() );
    entity.setOwnerNames( dto.getOwnerNames() );
    entity.setOwnerSurname( dto.getOwnerSurname() );
    entity.setEmail( dto.getEmail() );
    return entity;
  }

  public SupplierPojo fromEntityToPojo(Supplier entity) {
    SupplierPojo pojo = new SupplierPojo();
    pojo.setId( entity.getId() );
    pojo.setRegisterDate( entity.getRegisterDate() );
    pojo.setSupplierCode( entity.getSupplierCode() );
    pojo.setSupplierPhoneNumber( entity.getSupplierPhoneNumber() );
    pojo.setSupplierCelNumber( entity.getSupplierCelNumber() );
    pojo.setCompanyName( entity.getCompanyName() );
    pojo.setAddress( entity.getAddress() );
    pojo.setCompanyDescription( entity.getCompanyDescription() );
    pojo.setEnable( entity.getEnable() );
    pojo.setOwnerNames( entity.getOwnerNames() );
    pojo.setOwnerSurname( entity.getOwnerSurname() );
    pojo.setEmail( entity.getEmail() );
    pojo.setActive(entity.getActive());
    pojo.setCreatedDate(entity.getCreatedDate());
    pojo.setCreatedBy(entity.getCreatedBy());
    pojo.setLastModifiedBy(entity.getLastModifiedBy());

    return pojo;
  }

  public List<SupplierPojo> fromEntityListToPojoList(List<Supplier> entityList){
    return  entityList.stream()
            .map(this::fromEntityToPojo)
            .collect(Collectors.toList());
  }

  public PagePojo<SupplierPojo> toPagePojo(Page<Supplier> page) {
    PagePojo<SupplierPojo> dto = new PagePojo<>();

    dto.setContent(fromEntityListToPojoList(page.getContent()));
    dto.setLast(page.isLast());
    dto.setPageNumber(page.getNumber());
    dto.setPageSize(page.getSize());
    dto.setTotalPages(page.getTotalPages());
    dto.setTotalElements(page.getTotalElements());
    return dto;
  }

}
