package com.oengal.oengal.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

  private int bizErrorCode;
  private String message;

  public static class ErrorResponseBuilder {

    private int bizErrorCode;
    private String message;
    private ErrorResponse errorResponse;

    public ErrorResponseBuilder(){}

    public ErrorResponseBuilder bizErrorCode(int code) {
      this.bizErrorCode = code;
      return this;
    }

    public ErrorResponseBuilder message(String message) {
      this.message = message;
      return this;
    }

    public ErrorResponse build() {
      return new ErrorResponse(this.bizErrorCode, this.message);
    }

  }

}