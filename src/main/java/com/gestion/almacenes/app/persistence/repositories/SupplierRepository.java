package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.Supplier;
import com.gestion.almacenes.app.presentation.filters.SupplierFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends GenericRepository<Supplier, Integer>{

    boolean existsBySupplierCodeAndActiveIsTrue(String code);

    boolean existsBySupplierCodeAndIdNotAndActiveIsTrue(String code, Integer id);

    Optional<Supplier> findByIdAndActiveIsTrue(Integer id);

    Optional<Supplier> findBySupplierCodeAndActiveTrue(String supplierCode);

    List<Supplier> findAllByActiveIsTrue();

    Page<Supplier> findAll(Specification<Supplier> spec, Pageable pageable);

    @Query("""
              SELECT S
              FROM Supplier S
              WHERE S.active = TRUE
              AND ( :#{#filter.id} IS NULL OR S.id= :#{#filter.id} )
              AND ( :#{#filter.supplierCode} IS NULL OR lower(S.supplierCode) LIKE lower(concat('%', :#{#filter.supplierCode}, '%')) )
              AND ( :#{#filter.supplierPhoneNumber} IS NULL OR lower(S.supplierPhoneNumber) LIKE lower(concat('%', :#{#filter.supplierPhoneNumber}, '%')) )
              AND ( :#{#filter.supplierCelNumber} IS NULL OR lower(S.supplierCelNumber) LIKE lower(concat('%', :#{#filter.supplierCelNumber}, '%')) )
              AND ( :#{#filter.companyName} IS NULL OR lower(S.companyName) LIKE lower(concat('%', :#{#filter.companyName}, '%')) )
              AND ( :#{#filter.address} IS NULL OR lower(S.address) LIKE lower(concat('%', :#{#filter.address}, '%')) )
              AND ( :#{#filter.companyDescription} IS NULL OR lower(S.companyDescription) LIKE lower(concat('%', :#{#filter.companyDescription}, '%')) )
              AND ( :#{#filter.ownerNames} IS NULL OR lower(S.ownerNames) LIKE lower(concat('%', :#{#filter.ownerNames}, '%')) )
              AND ( :#{#filter.ownerSurname} IS NULL OR lower(S.ownerSurname) LIKE lower(concat('%', :#{#filter.ownerSurname}, '%')) )
              AND ( :#{#filter.email} IS NULL OR lower(S.email) LIKE lower(concat('%', :#{#filter.email}, '%')) )
              AND (
                (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
                OR lower(S.supplierCode) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.supplierPhoneNumber) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.supplierCelNumber) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.companyName) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.address) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.companyDescription) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.ownerNames) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.ownerSurname) LIKE lower(concat('%', :#{#filter.search}, '%'))
                OR lower(S.email) LIKE lower(concat('%', :#{#filter.search}, '%'))
              )
      """)
    Page<Supplier> pageable(SupplierFilter filter, Pageable pageable);

    List<Supplier> findAll(Specification<Supplier> spec);


}
