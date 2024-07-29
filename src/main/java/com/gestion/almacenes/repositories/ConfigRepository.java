package com.gestion.almacenes.repositories;

import com.gestion.almacenes.entities.Config;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConfigRepository extends JpaRepository<Config, Integer> {

  boolean existsByCodeAndActiveIsTrue(String code);

  boolean existsByCodeAndIdNotAndActiveIsTrue(String code, Integer id);

  Optional<Config> findByIdAndActiveIsTrue(Integer id);

  List<Config> findAllByActiveIsTrue();

  List<Config> findAll();

  Page<Config> findAll(Pageable pageable);

  @Query(value = """
      SELECT c.value FROM core.config c WHERE c.code = ?1 AND c.active=true limit 1
      """, nativeQuery = true)
  Optional<String> getValueByCode(String code);
}
