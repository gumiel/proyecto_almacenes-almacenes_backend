package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.presentation.filters.ProductFilter;
import com.gestion.almacenes.app.persistence.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<Product> findById(Integer id);
  
  Optional<Product> findByCodeAndActiveTrue(String code);

  Optional<Product> findByIdAndActiveIsTrue(Integer id);

  List<Product> findAll();

  Page<Product> findAll(Specification<Product> spec, Pageable pageable);

  List<Product> findAll(Specification<Product> spec);

  int countByCodeAndActiveTrue(String code);

  List<Product> findByDescription(String description);

  @Query(
      value = """
          SELECT
            p.id as id,
            p.code as code,
            p.name as name,
            p.description as description,
            p.approximate_unit_price as approximateUnitPrice
          FROM product p
          WHERE :term IS NULL
          OR lower(name) like CONCAT('%', lower(:term), '%')
          """,
      nativeQuery = true
  )
  Page<ProductProjection> search(@Param("term") String term, Pageable pageable);

  @Query("""
          SELECT P
          FROM Product P
          WHERE P.active = TRUE
          AND ( :#{#filter.id} IS NULL OR P.id= :#{#filter.id} )
          AND ( :#{#filter.code} IS NULL OR lower(P.code) LIKE lower(concat('%', :#{#filter.code}, '%')) )
          AND ( :#{#filter.name} IS NULL OR lower(P.name) LIKE lower(concat('%', :#{#filter.name}, '%')) )
          AND ( :#{#filter.description} IS NULL OR lower(P.description) LIKE lower(concat('%', :#{#filter.description}, '%')) )
          AND ( :#{#filter.approximateUnitPrice} IS NULL OR P.approximateUnitPrice= :#{#filter.approximateUnitPrice} )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(P.code) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(P.name) LIKE lower(concat('%', :#{#filter.search}, '%'))
            OR lower(P.description) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<Product> pageable(ProductFilter filter, Pageable pageable);
}
