package com.gestion.almacenes.app.domain.services_impls;

import com.gestion.almacenes.app.domain.entities.Supplier;
import com.gestion.almacenes.app.domain.mappers.SupplierMapper;
import com.gestion.almacenes.app.domain.services.SupplierService;
import com.gestion.almacenes.app.persistence.repositories.SupplierRepository;
import com.gestion.almacenes.app.presentation.dtos.SupplierDto;
import com.gestion.almacenes.app.presentation.filters.SupplierFilter;
import com.gestion.almacenes.app.presentation.pojos.SupplierPojo;
import com.gestion.almacenes.commons.util.PagePojo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.gestion.almacenes.app.domain.services_impls.ExceptionsCustom.*;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements
        SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public List<SupplierPojo> getAll() {
        return supplierMapper.fromEntityListToPojoList(
                supplierRepository.findAllByActiveIsTrue()
        );
    }

    @Override
    public SupplierPojo create(SupplierDto supplierdto) {

        if (supplierRepository.existsBySupplierCodeAndActiveIsTrue(supplierdto.getSupplierCode()))
            errorDuplicateInFieldCode(Supplier.class, "supplierCode", supplierdto.getSupplierCode());

        Supplier supplier = supplierMapper.fromDtoToEntity(supplierdto, null);
        supplier.setRegisterDate(LocalDateTime.now());

        return supplierMapper.fromEntityToPojo(
                supplierRepository.save(supplier)
        );
    }

    @Override
    public SupplierPojo update(Integer id, SupplierDto supplierdto) {

        Supplier supplierFound = this.findSupplierById(id);

        if (supplierRepository.existsBySupplierCodeAndIdNotAndActiveIsTrue(supplierdto.getSupplierCode(), supplierFound.getId()))
            errorDuplicateInFieldCode(Supplier.class, "supplierCode", supplierdto.getSupplierCode());

        Supplier supplier = supplierMapper.fromDtoToEntity(supplierdto, supplierFound);

        return supplierMapper.fromEntityToPojo(
                supplierRepository.save(supplier)
        );
    }

    @Override
    public SupplierPojo getById(Integer id) {
        return supplierMapper.fromEntityToPojo(
                this.findSupplierById(id)
        );
    }

    @Override
    public SupplierPojo getBySupplierCode(String supplierCode) {
        Supplier supplier = supplierRepository.findBySupplierCodeAndActiveTrue(supplierCode).orElseThrow(
                errorEntityNotFound(Supplier.class, "supplierCode", supplierCode)
        );
      return supplierMapper.fromEntityToPojo(supplier);
    }

    @Override
    public void delete(Integer id) {
        Supplier supplier = this.findSupplierById(id);
        supplierRepository.delete(supplier);
    }

    @Override
    public void disabled(Integer id) {
        Supplier supplier = this.findSupplierById(id);
        if (Boolean.TRUE.equals(supplier.getActive())) {
            supplier.setActive(false);
            supplierRepository.save(supplier);
        } else {
            errorAlreadyDeleted(Supplier.class, supplier.getId());
        }
    }

    public PagePojo<SupplierPojo> pageable(Integer pageNumber, Integer pageSize, String sortField, String sortOrder, SupplierFilter filter) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Supplier> supplierPage = supplierRepository.pageable(filter, pageable);

        return supplierMapper.toPagePojo(supplierPage);
    }

    private Supplier findSupplierById(Integer id){
        return supplierRepository.findByIdAndActiveIsTrue(id).orElseThrow(
                errorEntityNotFound(Supplier.class, id)
        );
    }

}