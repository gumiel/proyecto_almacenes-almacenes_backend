package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.Product;
import com.gestion.almacenes.app.domain.entities.Stock;
import com.gestion.almacenes.app.presentation.filters.StockFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {

  Optional<Stock> findByIdAndActiveIsTrue(Integer id);

  List<Stock> findAllByActiveIsTrue();

  List<Stock> findAll(Specification<Stock> spec);

  Page<Stock> findAll(Specification<Stock> spec, Pageable pageable);

  boolean existsByStorehouse_IdAndProduct_IdAndActiveTrue(Integer id, Integer id1);

  Optional<Stock> findByStorehouse_IdAndProduct_IdAndActiveTrue(Integer id, Integer id1);

  Page<Stock> findByActiveTrue(Pageable pageable);

  Page<Stock> findByStorehouse_Id(Integer id, Pageable pageable );

    @Query("""
            select s.product from Stock s
            where s.storehouse.id = :id
            and s.active = true
            and s.storehouse.active = true
            and s.product.active = true
            AND (:term IS NULL
            OR s.product.code like CONCAT('%', :term, '%')
            OR lower(s.product.name) like CONCAT('%', lower(:term), '%')
            OR lower(s.product.description) like CONCAT('%', lower(:term), '%'))
          """)
    Page<Product> findByStorehouseId(@Param("id") Integer id, @Param("term") String term, Pageable pageable);

  @Query("""
          SELECT S
          FROM Stock S
          WHERE S.active = TRUE
          AND ( :#{#filter.id} IS NULL OR S.id= :#{#filter.id} )
          AND ( :#{#filter.quantityInStock} IS NULL OR S.quantityInStock= :#{#filter.quantityInStock} )
          AND ( :#{#filter.minimumStock} IS NULL OR S.minimumStock= :#{#filter.minimumStock} )
          AND ( :#{#filter.maximumStock} IS NULL OR S.maximumStock= :#{#filter.maximumStock} )
          AND ( :#{#filter.unitPrice} IS NULL OR S.unitPrice= :#{#filter.unitPrice} )
          AND ( :#{#filter.storehouseId} IS NULL OR S.storehouse.id= :#{#filter.storehouseId} )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
          )
  """)
  Page<Stock> pageable(StockFilter filter, Pageable pageable);
}
