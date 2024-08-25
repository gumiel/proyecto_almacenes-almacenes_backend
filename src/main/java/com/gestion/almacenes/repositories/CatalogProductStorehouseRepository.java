package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.CatalogProductStorehouse;
import com.gestion.almacenes.entities.Product;
import com.gestion.almacenes.entities.Storehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CatalogProductStorehouseRepository extends JpaRepository<CatalogProductStorehouse, Integer>{

    Optional<CatalogProductStorehouse> findByIdAndActiveIsTrue(Integer id);

    List<CatalogProductStorehouse> findAllByActiveIsTrue();

    Page<CatalogProductStorehouse> findAll(Specification<CatalogProductStorehouse> spec, Pageable pageable);
    List<CatalogProductStorehouse> findAll(Specification<CatalogProductStorehouse> spec);

    CatalogProductStorehouse findByStorehouseId_IdAndProductId_Id(Integer id, Integer id1);
    boolean existsByStorehouseId_IdAndProductId_Id(Integer id, Integer id1);

    /**
     * Devuelve Una lista de ids todos los almacenes que no estan asociados al producto
     * @param productId Identificador del producto
     * @return
     */
    @Query(value =
            """
            select s.id
            from storehouse s
            where s.id not in (select s.id
            from catalog_product_storehouse cps
            right join storehouse s on s.id = cps.storehouse_id
            where cps.product_id = ?1 )
            """, nativeQuery = true)
    List<Integer> findStorehouseIdNotInByProductId(Integer productId);

    /**
     * Devuelve Una lista de ids de todos los almacenes asociados a un producto
     * @param productId Identificador del producto
     * @return
     */
    @Query(value =
            """
            select s.id
            from storehouse s
            inner join catalog_product_storehouse cps on cps.storehouse_id = s.id
            where cps.product_id = ?1
            """, nativeQuery = true)
    List<Integer> findStorehouseIdByProductId(Integer productId);

    void deleteByStorehouseAndProduct(Storehouse storehouse, Product product);
}
