package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.ConfigurationParameter;
import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.presentation.filters.ConfigurationParameterFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConfigurationParameterRepository extends JpaRepository<ConfigurationParameter, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  List<ConfigurationParameter> findByActiveTrue();

  Optional<Product> findByIdAndActiveIsTrue(Integer id);

  /*@Query("""
          SELECT C
          FROM ConfigurationParameter C
          WHERE C.active = TRUE
          AND ( :#{#filter.id} IS NULL OR C.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(C.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.value} IS NULL OR lower(C.value) LIKE lower(concat('%', :#{#filter.value}, '%')) )
          AND ( :#{#filter.description} IS NULL OR C.description= :#{#filter.description} )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(C.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(C.value) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(C.description) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
          """)
  Page<ConfigurationParameter> findAll(ConfigurationParameterFilter filter, Pageable pageable);*/
  @Query("""
          SELECT C
          FROM ConfigurationParameter C
          WHERE C.active = TRUE
          AND ( :#{#filter.id} IS NULL OR C.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(C.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.value} IS NULL OR lower(C.value) LIKE lower(concat('%', :#{#filter.value}, '%')) )
          AND ( :#{#filter.description} IS NULL OR lower(C.description) LIKE lower(concat('%', :#{#filter.description}, '%')) )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(C.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(C.value) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(C.description) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<ConfigurationParameter> findAll(ConfigurationParameterFilter filter, Pageable pageable);
}
