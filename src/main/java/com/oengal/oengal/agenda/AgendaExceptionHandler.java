package com.oengal.oengal.agenda;

import com.oengal.oengal.common.ErrorResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AgendaExceptionHandler {
  @ExceptionHandler(AgendaException.class)
  public ResponseEntity<ErrorResponse> returnAgendaException(AgendaException agendaException) {

    ErrorResponse errorResponse = new ErrorResponse
        .ErrorResponseBuilder()
        .bizErrorCode(agendaException.getCode())
        .message(agendaException.getMessage())
        .build();

    return ResponseEntity.status(agendaException.getHttpStatus()).body(errorResponse);

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> returnInvalidInputException(MethodArgumentNotValidException methodArgumentNotValidException) {

    ErrorResponse errorResponse = new ErrorResponse
        .ErrorResponseBuilder()
        .bizErrorCode(-101)
        .message(methodArgumentNotValidException.getBindingResult().getFieldError().
            getDefaultMessage())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> returnException(DataIntegrityViolationException dataIntegrityViolationException) {

    ErrorResponse errorResponse = new ErrorResponse
        .ErrorResponseBuilder()
        .bizErrorCode(-122)
        .message(dataIntegrityViolationException.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

  }

}
