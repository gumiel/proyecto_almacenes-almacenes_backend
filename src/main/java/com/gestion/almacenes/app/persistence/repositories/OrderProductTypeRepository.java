package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.OrderProductType;
import com.gestion.almacenes.app.presentation.filters.OrderProductTypeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderProductTypeRepository extends JpaRepository<OrderProductType, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<OrderProductType> findByIdAndActiveIsTrue(Integer id);
  
  Optional<OrderProductType> findByCodeAndActiveTrue(String code);

  List<OrderProductType> findAllByActiveIsTrue();

  List<OrderProductType> findAll(Specification<OrderProductType> spec);

  Page<OrderProductType> findAll(Specification<OrderProductType> spec, Pageable pageable);

  int countByCodeAndActiveTrue(String code);

  @Query("""
          SELECT O
          FROM OrderProductType O
          WHERE O.active = TRUE
          AND ( :#{#filter.id} IS NULL OR O.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(O.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.name} IS NULL OR lower(O.name) LIKE lower(concat('%', :#{#filter.name}, '%')) )
          AND ( :#{#filter.description} IS NULL OR lower(O.description) LIKE lower(concat('%', :#{#filter.description}, '%')) )
          AND ( :#{#filter.action} IS NULL OR lower(O.action) LIKE lower(concat('%', :#{#filter.action}, '%')) )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(O.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(O.name) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(O.description) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(O.action) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<OrderProductType> pageable(OrderProductTypeFilter filter, Pageable pageable);
}
