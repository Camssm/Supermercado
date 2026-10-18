package com.app.Producto.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.Producto.dto.ErrorDto;
import com.app.Producto.exception.BusinessException;
import com.app.Producto.exception.RequestException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDto> businessExceptionHandler(BusinessException ex, HttpServletRequest request) {
        return construir(ex.getStatus(), ex.getCode(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorDto> requestExceptionHandler(RequestException ex, HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST, ex.getCode(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> illegalArgumentHandler(IllegalArgumentException ex, HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST, "P-400", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado en {}", request.getRequestURI(), ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "P-500",
                "Error interno del servidor", request.getRequestURI());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
            HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        String mensaje = (body instanceof ProblemDetail pd && pd.getDetail() != null)
                ? pd.getDetail()
                : "Solicitud inválida";

        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorDto error = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .status(statusCode.value())
                .code("P-" + statusCode.value())
                .message(mensaje)
                .path(path)
                .build();

        return new ResponseEntity<>(error, headers, statusCode);
    }

    private ResponseEntity<ErrorDto> construir(HttpStatus status, String code, String message, String path) {
        ErrorDto error = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .code(code)
                .message(message)
                .path(path)
                .build();

        return new ResponseEntity<>(error, status);
    }
}