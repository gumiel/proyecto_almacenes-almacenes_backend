package com.gestion.almacenes.app.persistence.repositories;

import java.util.List;

public interface MyCustomRepository {
  void clearEntityManager();

  List<String> lista();

}