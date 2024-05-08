package com.back.confectionary.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Void> handlerOnlineStoreAPIException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handlerBadCredentialsError(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        new Date(),
                        HttpStatus.UNAUTHORIZED,
                        "Bad credentials",
                        webRequest.getDescription(false).replace("uri=", "")),
                HttpStatus.BANDWIDTH_LIMIT_EXCEEDED
        );
    }

    /*@ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handlerBadCredentialsError(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    }*/
}
