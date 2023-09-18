package com.ioidigital.application.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private final String code;
    private final String message;
}
