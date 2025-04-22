package com.gestion.almacenes.app.persistence.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyCustomRepositoryImpl implements MyCustomRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void clearEntityManager() {
    entityManager.clear();
  }

  @Override
  public List<String> lista() {
    Query query = entityManager.createNativeQuery("SELECT code FROM config", String.class);
//    Query query = entityManager.createNativeQuery("SELECT code FROM config where code = :code", String.class);
    //query.setParameter("code", "COD-153");
    List<String> list = query.getResultList();
    return list;
  }

}
