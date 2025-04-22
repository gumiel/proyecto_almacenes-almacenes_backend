package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.StorehouseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorehouseTypeRepository_bk extends JpaRepository<StorehouseType, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  List<StorehouseType> findByActiveTrue();

  Optional<StorehouseType> findByIdAndActiveIsTrue(Integer id);

  Page<StorehouseType> findAll(Specification<StorehouseType> spec, Pageable pageable);
}
