package com.rovio.plushmarket.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rovio.plushmarket.exception.PlushMarketException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Optional;

@RestControllerAdvice
public class PlushMarketControllerAdvice {
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(final InvalidFormatException e) {
        return error(e, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(final HttpMessageNotReadableException e) {
        return error(e, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runTimeException(final RuntimeException e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PlushMarketException.class)
    public ResponseEntity<ErrorResponse> plushMarketException(final PlushMarketException e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity <ErrorResponse> error(final Exception exception, final HttpStatus httpStatus) {
        final String message = Optional.ofNullable(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity(new ErrorResponse(httpStatus,message), httpStatus);
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class ErrorResponse {
        private HttpStatus httpStatus;
        private String errorMessage;
    }

}

