package com.gabrielluciano.reajustesalarial.error;

import com.gabrielluciano.reajustesalarial.exceptions.DuplicatedCpfException;
import com.gabrielluciano.reajustesalarial.exceptions.FuncionarioNotFoundException;
import com.gabrielluciano.reajustesalarial.exceptions.SalarioAlreadyReajustadoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

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

    @ExceptionHandler(FuncionarioNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleFuncionarioNotFoundException(
            FuncionarioNotFoundException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .error(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now(ZoneOffset.UTC).toString())
                        .build());
    }

    @ExceptionHandler(SalarioAlreadyReajustadoException.class)
    protected ResponseEntity<ErrorResponse> handleSalarioAlreadyReajustadoException(
            SalarioAlreadyReajustadoException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorResponse.builder()
                        .error(ex.getMessage())
                        .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now(ZoneOffset.UTC).toString())
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .error(convertFieldErrorsToString(ex.getFieldErrors()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .path(servletWebRequest.getRequest().getRequestURI())
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

    private String convertFieldErrorsToString(List<FieldError> fieldErrors) {
        String[] errors = new String[fieldErrors.size()];
        int i = 0;
        for (FieldError fieldError : fieldErrors) {
            errors[i] = String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
            i++;
        }
        return "Constraint Violation(s): " + String.join(", ", errors);
    }
}
