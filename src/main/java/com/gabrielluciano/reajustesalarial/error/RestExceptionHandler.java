package com.gabrielluciano.reajustesalarial.error;

import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicatedCpfException.class)
    protected ResponseEntity<ErrorResponse> handleDuplicatedCpfException(
            DuplicatedCpfException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .error(ex.getMessage())
                        .status(HttpStatus.CONFLICT.value())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now(ZoneOffset.UTC).toString())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;

        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .error(ex.getMessage())
                        .status(status.value())
                        .path(servletWebRequest.getRequest().getRequestURI())
                        .timestamp(LocalDateTime.now(ZoneOffset.UTC).toString())
                        .build());
    }
}
