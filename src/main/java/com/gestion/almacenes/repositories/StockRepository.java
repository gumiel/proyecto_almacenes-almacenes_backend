package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.Stock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {

  Optional<Stock> findByIdAndActiveIsTrue(Integer id);

  List<Stock> findAllByActiveIsTrue();

  List<Stock> findAll(Specification<Stock> spec);

  Page<Stock> findAll(Specification<Stock> spec, Pageable pageable);

  boolean existsByStorehouse_IdAndProduct_IdAndActiveTrue(Integer id, Integer id1);

  Optional<Stock> findByStorehouse_IdAndProduct_IdAndActiveTrue(Integer id, Integer id1);
}
