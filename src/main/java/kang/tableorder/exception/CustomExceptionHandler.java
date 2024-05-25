package kang.tableorder.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<CustomErrorResponse> handleCustomException(CustomException e) {
    CustomErrorResponse response = CustomErrorResponse.builder()
        .code(e.getErrorCode().getStatus().value())
        .message(e.getErrorCode().getValue())
        .build();

    return new ResponseEntity<>(response, e.getErrorCode().getStatus());
  }
}
