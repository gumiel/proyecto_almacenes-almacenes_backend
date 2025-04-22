package com.gestion.almacenes.commons.exception;


import com.gestion.almacenes.commons.exception.response.ErrorListResponse;
import com.gestion.almacenes.commons.exception.response.ErrorResponse;
import com.gestion.almacenes.commons.exception.response.FieldErrorModel;
import com.gestion.almacenes.commons.exception.response.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ErrorHandler {

  /*
   * Error for Method not supported (Code: 405)
   * */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpServletRequest req,
      Exception ex) {
    HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(),
        req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }

  /*
   * Error for message not readable  (Code: 400)
   * */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handlerMessageNotReadableException(HttpServletRequest req,
      Exception ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(),
        req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }

  /*
   *
   * */
  @ExceptionHandler(EntityNotFound.class)
  public ResponseEntity<ErrorResponse> handlerEntityNotFoundException(HttpServletRequest req,
      Exception ex) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(),
        req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity<ErrorResponse> handlerDuplicateException(HttpServletRequest req,
      Exception ex) {
    HttpStatus status = HttpStatus.CONFLICT;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(),
        req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }


  @ExceptionHandler(AlreadyDeletedException.class)
  public ResponseEntity<ErrorResponse> handlerAlreadyDeletedException(HttpServletRequest req,
      Exception ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(),
        req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> handleValidException(
      HttpServletRequest req, MethodArgumentNotValidException ex) {

    List<FieldErrorModel> errors = ex.getBindingResult().getAllErrors().stream().map(fieldError -> {
      FieldErrorModel fieldErrorModel = new FieldErrorModel();
      fieldErrorModel.setCode(fieldError.getCode());
      fieldErrorModel.setMessage(fieldError.getDefaultMessage());
      fieldErrorModel.setField(((FieldError) fieldError).getField());
      return fieldErrorModel;
    }).toList();

    HttpStatus status = HttpStatus.BAD_REQUEST;
    ValidationErrorResponse response = new ValidationErrorResponse();
    response.setCode(status.value());
    response.setStatus(status.name());
    response.setPath(req.getRequestURI());
    response.setErrors(errors);

    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(ErrorListException.class)
  public ResponseEntity<ErrorListResponse> handleErrorsException(HttpServletRequest req,
      ErrorListException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorListResponse errorResponse = new ErrorListResponse();
    errorResponse.setCode(status.value());
    errorResponse.setErrorList(ex.getErrorList());
    errorResponse.setStatus(status.name());
    errorResponse.setPath(req.getRequestURI());
    return ResponseEntity.status(status).body(errorResponse);
  }

  @ExceptionHandler(ValidationErrorException.class)
  public ResponseEntity<ErrorResponse> handlerValidationErrorException(HttpServletRequest req,
      Exception ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(),
        req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(HttpServletRequest req, DataIntegrityViolationException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ErrorResponse response = new ErrorResponse(status.value(), status.name(),
            "Error de integridad de datos: no se puede eliminar el registro debido a relaciones con otros registros.",
            req.getRequestURI());
    return ResponseEntity.status(status).body(response);
  }

}
