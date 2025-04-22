
package com.gestion.almacenes.commons;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {

  boolean existsByCode(String code);

  boolean existsByCodeAndIdNot(String code, Integer id);

  Report getByCode(String code);

}
