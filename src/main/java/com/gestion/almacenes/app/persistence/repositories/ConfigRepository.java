package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.Config;
import com.gestion.almacenes.app.presentation.filters.ConfigFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long>, MyCustomRepository {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<Config> findByIdAndActiveIsTrue(Integer id);

  List<Config> findAllByActiveIsTrue();

  List<Config> findAll();

  Page<Config> findAll(Pageable pageable);

  Optional<Config> findByCodeAndActiveTrue(String code);

  @Query("""
          SELECT C
          FROM Config C
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
  Page<Config> pageable(ConfigFilter filter, Pageable pageable);
}
