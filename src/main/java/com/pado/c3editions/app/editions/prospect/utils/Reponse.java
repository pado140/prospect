package com.pado.c3editions.app.editions.prospect.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reponse<T> {
	private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String reson,path,developpermessage;
    private int statuscode;
    private Map<?,T> data;
}