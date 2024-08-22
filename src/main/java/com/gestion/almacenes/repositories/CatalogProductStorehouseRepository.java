package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.CatalogProductStorehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogProductStorehouseRepository extends JpaRepository<CatalogProductStorehouse, Integer>{

    Optional<CatalogProductStorehouse> findByIdAndActiveIsTrue(Integer id);

    List<CatalogProductStorehouse> findAllByActiveIsTrue();

    Page<CatalogProductStorehouse> findAll(Specification<CatalogProductStorehouse> spec, Pageable pageable);
    List<CatalogProductStorehouse> findAll(Specification<CatalogProductStorehouse> spec);


    boolean existsByStorehouseId_IdAndProductId_Id(Integer id, Integer id1);

    CatalogProductStorehouse findByStorehouseId_IdAndProductId_Id(Integer id, Integer id1);

}
