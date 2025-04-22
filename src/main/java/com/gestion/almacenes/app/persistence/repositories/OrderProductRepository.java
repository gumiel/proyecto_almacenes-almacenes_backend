package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.OrderProduct;
import com.gestion.almacenes.app.presentation.filters.OrderProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<OrderProduct> findByIdAndActiveIsTrue(Integer id);
  
  Optional<OrderProduct> findByCodeAndActiveTrue(String code);

  List<OrderProduct> findAllByActiveIsTrue();

  Page<OrderProduct> findAll(Specification<OrderProduct> spec, Pageable pageable);

  List<OrderProduct> findAll(Specification<OrderProduct> spec);

  @Query("select o from OrderProduct o where o.orderProductType.code = ?1 and o.active = true order by o.id DESC")
  Page<OrderProduct> findOrderProductByOrderProductType_CodeAndActiveTrueOrderByIdDesc(String code,
      Pageable pageable);

  @Query("""
          SELECT O
          FROM OrderProduct O
          WHERE O.active = TRUE
          AND ( :#{#filter.id} IS NULL OR O.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(O.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.registrationDate} IS NULL OR O.registrationDate= :#{#filter.registrationDate} )
          AND ( :#{#filter.registrationTime} IS NULL OR O.registrationTime= :#{#filter.registrationTime} )
          AND ( :#{#filter.description} IS NULL OR lower(O.description) LIKE lower(concat('%', :#{#filter.description}, '%')) )
          AND ( :#{#filter.status} IS NULL OR lower(O.status) LIKE lower(concat('%', :#{#filter.status}, '%')) )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(O.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(O.description) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(O.status) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<OrderProduct> pageable(OrderProductFilter filter, Pageable pageable);
}
