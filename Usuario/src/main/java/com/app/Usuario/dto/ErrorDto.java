package com.app.Usuario.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private String path;
}