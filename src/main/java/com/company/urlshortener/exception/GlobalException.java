package com.company.urlshortener.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {

        log.error(ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(
                new GlobalErrorMessage(ex.hashCode(),ex.getLocalizedMessage(),new Date()),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }



}
