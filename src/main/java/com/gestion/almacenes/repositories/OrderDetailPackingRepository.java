package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.OrderDetailPacking;
import com.gestion.almacenes.entities.OrderProductDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailPackingRepository extends JpaRepository<OrderDetailPacking, Integer> {

    long deleteByOrderProductDetail(OrderProductDetail orderProductDetail);

    List<OrderDetailPacking> findByOrderProductDetail_IdAndActiveTrue(Integer id);

}