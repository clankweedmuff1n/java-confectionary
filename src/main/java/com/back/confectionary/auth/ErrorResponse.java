package com.back.confectionary.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class ErrorResponse {
    private final Date timestamp;
    private final HttpStatus status;
    private final String message;
    private final String path;
}
