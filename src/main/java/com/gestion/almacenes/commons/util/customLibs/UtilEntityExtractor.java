package com.gestion.almacenes.commons.util.customLibs;

@FunctionalInterface
public interface UtilEntityExtractor<T, R> {
  R extract(T t);

  static <T, R> R getValueOrNull(T entity, UtilEntityExtractor<T, R> extractor) {
    return (entity != null) ? extractor.extract(entity) : null;
  }

}
