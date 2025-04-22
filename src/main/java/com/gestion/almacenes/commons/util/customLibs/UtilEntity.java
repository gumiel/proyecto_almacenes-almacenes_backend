package com.gestion.almacenes.commons.util.customLibs;

import com.gestion.almacenes.commons.exception.InvalidValueException;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

/**
 * Clase estática con utilitarios para Entidades
 */
public class UtilEntity {

    /**
     * Método que devuelve el identificador (ID) de una objeto (Entidad)
     * verificando antes si ese Objeto (Entidad) no llega como nulo
     * @param entity Cualquier Entidad
     * @return Identificador Entero o Nulo
     */
    public static Integer getIdOrNull(Object entity) {
        if (entity == null) {
            return null;
        }

        if (entity instanceof Integer) {
            throw new InvalidValueException(String.format("La entidad %s no puede ser del tipo entero", entity.getClass().getSimpleName()));
        }

        try {
            var method = entity.getClass().getMethod("getId");
            return (Integer) method.invoke(entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new InvalidValueException(String.format("La entidad %s no tiene un método getId válido", entity.getClass().getSimpleName()));
        }
    }

    /**
     * Método que devuelve el identificador externo (externalId) de una objeto (Entidad externa)
     * verificando antes si ese Objeto (Entidad) no llega como nulo
     * @param entity Cualquier Entidad externa (External)
     * @return Identificador externo (externalId) Entero o Nulo
     */
    public static Integer getExternalIdOrNull(Object entity) {
        if (entity == null) {
            return null;
        }

        if (entity instanceof Integer) {
            throw new InvalidValueException(String.format("La entidad %s no puede ser del tipo entero", entity.getClass().getSimpleName()));
        }

        try {
            var method = entity.getClass().getMethod("getExternalId");
            return (Integer) method.invoke(entity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new InvalidValueException(
                String.format("La entidad %s no tiene un método getExternalId válido",
                    entity.getClass().getSimpleName()
                )
            );
        }
    }

    /**
     * Devuelve el tipo de dato necesario en el segundo parámetro según su tipo de respuesta
     * @param entity Entidades
     * @param getter Método de la entidad que vá devolver
     * @return Dato devuelto según su método
     * @param <T> Tipo de clase de la entidad del primer parámetro
     */
    public static <T> T getValueOrNull(Object entity, Function<Object, T> getter) {
        if (entity == null) {
            return null;
        }

        try {
            return getter.apply(entity);
        } catch (Exception e) {
            throw new InvalidValueException(
                String.format(
                    "La entidad %s no tiene un método (%s) válido",
                    entity.getClass().getSimpleName(),
                    getter.toString()
                )
            );
        }
    }
}
