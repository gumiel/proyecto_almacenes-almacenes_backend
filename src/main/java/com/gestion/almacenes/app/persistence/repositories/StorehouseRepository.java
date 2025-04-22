package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.Storehouse;
import com.gestion.almacenes.app.presentation.filters.StorehouseFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StorehouseRepository extends JpaRepository<Storehouse, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<Storehouse> findByIdAndActiveIsTrue(Integer id);

  Optional<Storehouse> findByCodeAndActiveTrue(String code);

  /**
   * Devuelve una lista de Almacenes activos
   * @return Lista de almacenes activos
   */
  List<Storehouse> findByActiveTrue();

  long countByCodeAndActiveTrue(String code);

  @Query("""
          SELECT S
          FROM Storehouse S
          WHERE S.active = TRUE
          AND ( :#{#filter.id} IS NULL OR S.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(S.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.name} IS NULL OR lower(S.name) LIKE lower(concat('%', :#{#filter.name}, '%')) )
          AND ( :#{#filter.description} IS NULL OR lower(S.description) LIKE lower(concat('%', :#{#filter.description}, '%')) )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(S.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(S.name) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(S.description) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<Storehouse> pageable(StorehouseFilter filter, Pageable pageable);

}