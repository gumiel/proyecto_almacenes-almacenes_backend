package com.gestion.almacenes.app.domain.services;

import com.gestion.almacenes.commons.util.PagePojo;
import com.gestion.almacenes.app.presentation.dtos.SupplierDto;
import com.gestion.almacenes.app.presentation.filters.SupplierFilter;
import com.gestion.almacenes.app.presentation.pojos.SupplierPojo;

import java.util.List;


public interface SupplierService{

    List<SupplierPojo> getAll();

    SupplierPojo create(SupplierDto dto);

    SupplierPojo update(Integer id, SupplierDto dto);

    SupplierPojo getById(Integer id);

    SupplierPojo getBySupplierCode(String supplierCode);

    void delete(Integer id);

    void disabled(Integer id);

    PagePojo<SupplierPojo> pageable(Integer page, Integer size, String sortField, String sortOrder, SupplierFilter filter);
}
