package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.OrderProductDetail;
import com.gestion.almacenes.app.presentation.filters.OrderProductDetailFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderProductDetailRepository extends JpaRepository<OrderProductDetail, Integer> {

  Optional<OrderProductDetail> findByIdAndActiveIsTrue(Integer id);

  List<OrderProductDetail> findAllByActiveIsTrue();

  Page<OrderProductDetail> findAll(Specification<OrderProductDetail> spec, Pageable pageable);

  List<OrderProductDetail> findAll(Specification<OrderProductDetail> spec);

  List<OrderProductDetail> findByOrderProduct_IdAndActiveTrue(Integer id);


  boolean existsByOrderProduct_IdAndStock_Storehouse_IdAndStock_Product_IdAndActiveTrue(Integer id,
      Integer id1, Integer id2);

  @Query("""
          select o from OrderProductDetail o
          where (o.orderProduct.id = ?1 OR ?1 IS NULL)
          and o.orderProduct.active = true 
          and o.active = true""")
  Page<OrderProductDetail> findByOrderProduct_IdAndOrderProduct_ActiveTrueAndActiveTrue(Integer id, Pageable pageable);

  @Query("""
          SELECT O
          FROM OrderProductDetail O
          WHERE O.active = TRUE
          AND ( :#{#filter.id} IS NULL OR O.id= :#{#filter.id} )
          AND ( :#{#filter.quantity} IS NULL OR O.quantity= :#{#filter.quantity} )
          AND ( :#{#filter.codeProduct} IS NULL OR lower(O.codeProduct) LIKE lower(concat('%', :#{#filter.codeProduct}, '%')) )
          AND ( :#{#filter.expirationDateProduct} IS NULL OR O.expirationDateProduct= :#{#filter.expirationDateProduct} )
          AND ( :#{#filter.price} IS NULL OR O.price= :#{#filter.price} )
          AND (
            (:#{#filter.search} IS NULL OR :#{#filter.search} = '')
            OR lower(O.codeProduct) LIKE lower(concat('%', :#{#filter.search}, '%'))
          )
  """)
  Page<OrderProductDetail> pageable(OrderProductDetailFilter filter, Pageable pageable);

  /**
   * Método para de devolver los valores similares segun el SortBy que se envia
   * @param field Campo de ordenamiento
   * @return campo similar al ordenamiento
   */
  default String sortChangeSimilar(String field){
    return Map.of(
        "entidadHijo1Id", "entidadHijo1.id",
        "entidadHijo2Id", "entidadHijo2.id"
    ).getOrDefault(field, field);

  }
}
