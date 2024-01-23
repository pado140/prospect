package com.pado.c3editions.app.editions.prospect.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String path;
    private Object error;
    private Object data;
}
