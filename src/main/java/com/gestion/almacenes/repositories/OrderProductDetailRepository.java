package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.OrderProductDetail;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductDetailRepository extends JpaRepository<OrderProductDetail, Integer> {

  Optional<OrderProductDetail> findByIdAndActiveIsTrue(Integer id);

  List<OrderProductDetail> findAllByActiveIsTrue();

  Page<OrderProductDetail> findAll(Specification<OrderProductDetail> spec, Pageable pageable);

  List<OrderProductDetail> findAll(Specification<OrderProductDetail> spec);

  List<OrderProductDetail> findByOrderProduct_IdAndActiveTrue(Integer id);


  boolean existsByOrderProduct_IdAndStock_Storehouse_IdAndStock_Product_IdAndActiveTrue(Integer id,
      Integer id1, Integer id2);
}
