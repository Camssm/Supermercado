package com.app.Usuario.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.Usuario.dto.ErrorDto;
import com.app.Usuario.exception.BusinessException;
import com.app.Usuario.exception.RequestException;

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
        return construir(HttpStatus.BAD_REQUEST, "U-400", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> integridadHandler(DataIntegrityViolationException ex,
                                                      HttpServletRequest request) {
        log.warn("Violación de integridad en {}: {}", request.getRequestURI(),
                ex.getMostSpecificCause().getMessage());
        return construir(HttpStatus.CONFLICT, "U-409",
                "La operación viola una restricción de datos (por ejemplo, un email ya registrado)",
                request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("Error inesperado en {}", request.getRequestURI(), ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "U-500",
                "Error interno del servidor", request.getRequestURI());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> errores.putIfAbsent(fe.getField(), fe.getDefaultMessage()));

        ErrorDto error = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("U-400")
                .message("Hay errores de validación en la solicitud")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .errores(errores)
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ErrorDto error = ErrorDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("U-400")
                .message("El cuerpo de la solicitud es inválido o está mal formado")
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(error);
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
                .code("U-" + statusCode.value())
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