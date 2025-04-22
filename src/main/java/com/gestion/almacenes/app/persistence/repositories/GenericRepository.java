package com.gestion.almacenes.app.persistence.repositories;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Field;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {

  // Obtiene la clase de la entidad
  //Class<T> getEntityClass();

  /**
   * Metodo para saber si un registro puede ser eliminado de forma lógica revisando si tiene relación con
   * otras tablas
   * @param tableName  Nombre de la tabla Ej. acq.suppliers
   * @param columnName Identificador unico Ej. id
   * @param idValue El valor del identificador que se quiere validar
   * @return True si puede eliminar y False si no puede eliminar (Porque tiene relación con otras tablas)
   */
  @Query(value = "SELECT public.can_delete_record(:table_name, :column_name, :id_value)", nativeQuery = true)
  boolean canDeleteRecord(@Param("table_name") String tableName,
      @Param("column_name") String columnName,
      @Param("id_value") Integer idValue);

  // Obtiene el nombre de la tabla desde la entidad
  default String getTableName(Class<T> entityClass) {
    if (entityClass.isAnnotationPresent(Table.class)) {
      Table tableAnnotation = entityClass.getAnnotation(Table.class);

      return (tableAnnotation != null && !tableAnnotation.schema().isEmpty())
          ?tableAnnotation.schema()+"."+tableAnnotation.name()
          : tableAnnotation.name();

    } else {
      return entityClass.getSimpleName().toLowerCase();
    }
  }

  // Obtiene el nombre de la columna del identificador
  default String getIdColumnName(Class<T> entityClass) {
    for (Field field : entityClass.getDeclaredFields()) {
      if (field.isAnnotationPresent(Id.class)) {
        // Si el campo tiene la anotación @Column, usa su valor
        if (field.isAnnotationPresent(Column.class)) {
          Column columnAnnotation = field.getAnnotation(Column.class);
          return columnAnnotation.name();
        }
        // Si no tiene @Column, devuelve el nombre del campo
        return field.getName();
      }
    }
    throw new IllegalStateException("No se encontró un campo con la anotación @Id en " + entityClass.getName());
  }
}


// @NoRepositoryBean
// public interface GenericRepository<T, ID> extends JpaRepository<T, ID>,
// JpaSpecificationExecutor<T> {
//
// // Obtiene la clase de la entidad
// //Class<T> getEntityClass();
//
// /**
//  * Metodo para saber si un registro puede ser eliminado de forma lógica revisando si tiene relación con
//  * otras tablas
//  * @param tableName  Nombre de la tabla Ej. acq.suppliers
// * @param columnName Identificador unico Ej. id
// * @param idValue El valor del identificador que se quiere validar
// * @return True si puede eliminar y False si no puede eliminar (Porque tiene relación con otras tablas)
// */
//@Query(value = "SELECT public.can_delete_record(:table_name, :column_name, :id_value)", nativeQuery = true)
//boolean canDeleteRecord(@Param("table_name") String tableName,
//    @Param("column_name") String columnName,
//    @Param("id_value") Integer idValue);
//
//// Obtiene el nombre de la tabla desde la entidad
//default String getTableName(Class<T> entityClass) {
//  if (entityClass.isAnnotationPresent(Table.class)) {
//    Table tableAnnotation = entityClass.getAnnotation(Table.class);
//
//    return (tableAnnotation != null && !tableAnnotation.schema().isEmpty())
//        ?tableAnnotation.schema()+"."+tableAnnotation.name()
//        : tableAnnotation.name();
//
//  } else {
//    return entityClass.getSimpleName().toLowerCase();
//  }
//}
//
//// Obtiene el nombre de la columna del identificador
//default String getIdColumnName(Class<T> entityClass) {
//  for (Field field : entityClass.getDeclaredFields()) {
//    if (field.isAnnotationPresent(Id.class)) {
//      // Si el campo tiene la anotación @Column, usa su valor
//      if (field.isAnnotationPresent(Column.class)) {
//        Column columnAnnotation = field.getAnnotation(Column.class);
//        return columnAnnotation.name();
//      }
//      // Si no tiene @Column, devuelve el nombre del campo
//      return field.getName();
//    }
//  }
//  throw new IllegalStateException("No se encontró un campo con la anotación @Id en " + entityClass.getName());
//}
//
//  /*
//  default boolean canDeleteRecordGeneric(ID idValue, T table) {
//    String tableName = getTableName(getEntityClass());
//    String columnName = getIdColumnName(getEntityClass());
//    return canDeleteRecord(tableName, columnName, (Integer) idValue);
//  }
//  */
//
//
//  /*
//  private String getTableName() {
//    Class<?> entityClass = getGenericType();
//    Table tableAnnotation = entityClass.getAnnotation(Table.class);
//    if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
//      return tableAnnotation.name();
//    }
//    return entityClass.getSimpleName().toLowerCase(); // Valor predeterminado
//  }
//  */
//
//  /*
//  default String getPrimaryKeyColumnName() {
//    Class<?> entityClass = getGenericType();
//    for (Field field : entityClass.getDeclaredFields()) {
//      if (field.isAnnotationPresent(Id.class)) {
//        if (field.isAnnotationPresent(Column.class)) {
//          return field.getAnnotation(Column.class).name();
//        }
//        return field.getName();
//      }
//    }
//    throw new IllegalStateException("No se encontró un campo con @Id en la entidad " + entityClass.getName());
//  }
//  */
//
//
//
//  /*@SuppressWarnings("unchecked")
//  private Class<?> getGenericType() {
//    return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//  }*/
//
//}
