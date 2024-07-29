package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.StorehouseProduct;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorehouseProductRepository extends JpaRepository<StorehouseProduct, Integer> {

  Optional<StorehouseProduct> findByIdAndActiveIsTrue(Integer id);

  List<StorehouseProduct> findAllByActiveIsTrue();

  boolean existsByStorehouseId_IdAndProductId_Id(Integer id, Integer id1);

  StorehouseProduct findByStorehouseId_IdAndProductId_Id(Integer id, Integer id1);
}
