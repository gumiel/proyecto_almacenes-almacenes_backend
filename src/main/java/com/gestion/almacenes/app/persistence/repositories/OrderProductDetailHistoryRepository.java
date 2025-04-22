package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.OrderProductDetailHistory;
import com.gestion.almacenes.app.persistence.projections.OrderProductDetailHistoryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderProductDetailHistoryRepository extends JpaRepository<OrderProductDetailHistory, Integer>{

    Optional<OrderProductDetailHistory> findByIdAndActiveIsTrue(Integer id);

    List<OrderProductDetailHistory> findAllByActiveIsTrue();

    Page<OrderProductDetailHistory> findAll(Specification<OrderProductDetailHistory> spec, Pageable pageable);
    List<OrderProductDetailHistory> findAll(Specification<OrderProductDetailHistory> spec);

    Optional<OrderProductDetailHistory> findByStock_IdAndActiveTrue(Integer id);

    @Query(value="""
            SELECT
            o.id as id,
            o.order_product_id as orderProductId,
            o.order_product_detail_id as orderProductDetailId,
            o.stock_id as stockId,
            o.date_time_register as dateTimeRegister,
            o.quantity as quantity,
            o.price as price,
            o.average_price as averagePrice,
            o.physical_balance as physicalBalance,
            o.valued_balance as valuedBalance
            FROM order_product_detail_history o
            WHERE o.stock_id = :id
            ORDER BY date_time_register desc
            LIMIT 1
            """, nativeQuery = true)
    Optional<OrderProductDetailHistoryProjection> findByStockIdAndActiveTrue(@Param("id") Integer id);

    @Query(value="""
            SELECT
            o.id as id,
            o.order_product_id as orderProductId,
            o.order_product_detail_id as orderProductDetailId,
            o.stock_id as stockId,
            o.date_time_register as dateTimeRegister,
            o.quantity as quantity,
            o.price as price,
            o.average_price as averagePrice,
            o.physical_balance as physicalBalance,
            o.valued_balance as valuedBalance,
            s.code as storehouseCode,
            s.name as storehouseName,
            op.code as orderProductCode,
            op.description as orderProductDescription,
            opd.price as orderProductDetailPrice,
            opd.quantity as orderProductDetailQuantity
            FROM order_product_detail_history o
            inner join order_product_detail opd on opd.id = o.order_product_detail_id
            inner join order_product op on op.id = opd.order_product_id
            inner join storehouse s on s.id = op.storehouse_id
            WHERE o.stock_id = :id
            ORDER BY date_time_register desc
            """, nativeQuery = true)
    List<OrderProductDetailHistoryProjection> findAllByStockIdAndActiveTrue(@Param("id") Integer id);

    @Query(value="""
            SELECT
            o.id as id,
            o.order_product_id as orderProductId,
            o.order_product_detail_id as orderProductDetailId,
            o.stock_id as stockId,
            o.date_time_register as dateTimeRegister,
            o.quantity as quantity,
            o.price as price,
            o.average_price as averagePrice,
            o.physical_balance as physicalBalance,
            o.valued_balance as valuedBalance,
            s.code as storehouseCode,
            s.name as storehouseName,
            op.code as orderProductCode,
            op.description as orderProductDescription,
            opd.price as orderProductDetailPrice,
            opd.quantity as orderProductDetailQuantity,
            opt.action as orderProductTypeAction
            FROM order_product_detail_history o
            inner join order_product_detail opd on opd.id = o.order_product_detail_id
            inner join order_product op on op.id = opd.order_product_id
            inner join storehouse s on s.id = op.storehouse_id
            inner join order_product_type opt on opt.id = op.order_product_type_id
            WHERE o.stock_id = :id
            """, nativeQuery = true)
    Page<OrderProductDetailHistoryProjection> pageableByStockId(@Param("id") Integer id, Pageable pageable);

}
