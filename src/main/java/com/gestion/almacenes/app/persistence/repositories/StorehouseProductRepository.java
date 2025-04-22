package com.gestion.almacenes.app.persistence.repositories;

import com.gestion.almacenes.app.domain.entities.CatalogProductStorehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorehouseProductRepository extends JpaRepository<CatalogProductStorehouse, Integer> {


}
