package com.oengal.oengal.agenda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class AgendaException extends RuntimeException {

  private HttpStatus httpStatus;
  private int code;

  public AgendaException(HttpStatus httpStatus, int code, String msg) {
    super(msg);
    this.httpStatus = httpStatus;
    this.code = code;
  }

  public AgendaException(HttpStatus httpStatus, int code, Throwable throwable) {
    super(throwable.getMessage());
    this.httpStatus = httpStatus;
    this.code = code;
  }

}
