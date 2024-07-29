package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.UnitMeasurement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitMeasurementRepository extends JpaRepository<UnitMeasurement, Integer> {

  boolean existsByCodeUnitAndActiveIsTrue(String code);

  boolean existsByCodeUnitAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<UnitMeasurement> findByIdAndActiveIsTrue(Integer id);

  List<UnitMeasurement> findAllByActiveIsTrue();

  Page<UnitMeasurement> findAll(Specification<UnitMeasurement> spec, Pageable pageable);

  List<UnitMeasurement> findAll(Specification<UnitMeasurement> spec);

}
