package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.UnitMeasurement;
import com.gestion.almacenes.app.presentation.filters.UnitMeasurementFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnitMeasurementRepository extends JpaRepository<UnitMeasurement, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<UnitMeasurement> findByIdAndActiveIsTrue(Integer id);
  Optional<UnitMeasurement> findByCodeAndActiveTrue(String code);

  List<UnitMeasurement> findAllByActiveIsTrue();

  Page<UnitMeasurement> findAll(Specification<UnitMeasurement> spec, Pageable pageable);

  List<UnitMeasurement> findAll(Specification<UnitMeasurement> spec);

  int countByCodeAndActiveTrue(String code);

  @Query("""
          SELECT U
          FROM UnitMeasurement U
          WHERE U.active = TRUE
          AND ( :#{#filter.id} IS NULL OR U.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(U.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.name} IS NULL OR lower(U.name) LIKE lower(concat('%', :#{#filter.name}, '%')) )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(U.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(U.name) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<UnitMeasurement> pageable(UnitMeasurementFilter filter, Pageable pageable);
}
