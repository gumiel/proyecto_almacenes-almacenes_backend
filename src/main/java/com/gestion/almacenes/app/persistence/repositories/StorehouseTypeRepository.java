package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.StorehouseType;
import com.gestion.almacenes.app.presentation.filters.StorehouseTypeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StorehouseTypeRepository extends JpaRepository<StorehouseType, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<StorehouseType> findByIdAndActiveIsTrue(Integer id);
  
  Optional<StorehouseType> findByCodeAndActiveTrue(String code);

  List<StorehouseType> findAllByActiveIsTrue();

  List<StorehouseType> findAll(Specification<StorehouseType> spec);

  Page<StorehouseType> findAll(Specification<StorehouseType> spec, Pageable pageable);

  @Query("""
          SELECT S
          FROM StorehouseType S
          WHERE S.active = TRUE
          AND ( :#{#filter.id} IS NULL OR S.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(S.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.name} IS NULL OR lower(S.name) LIKE lower(concat('%', :#{#filter.name}, '%')) )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(S.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(S.name) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<StorehouseType> pageable(StorehouseTypeFilter filter, Pageable pageable);

  long countByCodeAndActiveTrue(String code);
}
