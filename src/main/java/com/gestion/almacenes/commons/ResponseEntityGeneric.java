package com.gestion.almacenes.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ResponseEntityGeneric {

    /**
     * Metodo que devuelve el objeto o la lista de objetos con el "ResponseEntity" correspondiente
     * @param body Objeto o Lista de objetos
     * @return "ResponseEntity" especifico para el metodo GET
     * @param <T> Respuesta generica de "ResponseEntity"
     */
    public static <T> ResponseEntity<T> statusGET(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /**
     * Medoto para los "status POST" que generaliza la respuesta "ResponseEntity"
     * @param body Objeto o Lista de objetos
     * @param status Tipo de estado que respondera
     * @return "ResponseEntity" especifico para el metodo POST
     * @param <T> Respuesta generica de "ResponseEntity"
     */
    public static <T> ResponseEntity<T> statusPOST(T body, HttpStatus status) {
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Medoto para los "status POST" que generaliza la respuesta "ResponseEntity"
     * @param body Objeto o Lista de objetos
     * @return "ResponseEntity" especifico para el metodo POST
     * @param <T> Respuesta generica de "ResponseEntity"
     */
    public static <T> ResponseEntity<T> statusPOST(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static ResponseEntity<Void> statusPOST(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }

    /**
     * Medoto para los "status POST" que generaliza la respuesta "ResponseEntity"
     * @return "ResponseEntity" especifico para el metodo POST
     */
    public static ResponseEntity<Void> statusPOST() {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Medoto para los "status PUT" que generaliza la respuesta "ResponseEntity"
     * @param body Objeto o Lista de objetos
     * @param status Tipo de estado que respondera
     * @return "ResponseEntity" especifico para el metodo PUT
     * @param <T> Respuesta generica de "ResponseEntity"
     */
    public static <T> ResponseEntity<T> statusPUT(T body, HttpStatus status) {
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Medoto para los "status PUT" que generaliza la respuesta "ResponseEntity"
     * @param body Objeto o Lista de objetos
     * @return "ResponseEntity" especifico para el metodo PUT
     * @param <T> Respuesta generica de "ResponseEntity"
     */
    public static <T> ResponseEntity<T> statusPUT(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /**
     * Medoto para los "status PUT" que generaliza la respuesta "ResponseEntity"
     * @param status Tipo de estado que respondera
     * @return "ResponseEntity" especifico para el metodo PUT
     */
    public static ResponseEntity<Void> statusPUT(HttpStatus status) {
        return ResponseEntity.status(status).build();
    }

    /**
     * Medoto para los "status PUT" que generaliza la respuesta "ResponseEntity"
     * @return "ResponseEntity" especifico para el metodo PUT
     */
    public static ResponseEntity<Void> statusPUT() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Medoto para los "status DELETE" que generaliza la respuesta "ResponseEntity"
     * @return "ResponseEntity" especifico para el metodo DELETE
     */
    public static ResponseEntity<Void> statusDELETE() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
