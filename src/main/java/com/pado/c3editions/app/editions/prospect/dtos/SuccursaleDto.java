package com.pado.c3editions.app.editions.prospect.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SuccursaleDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
//    private UsersDto createdby;
    private String name;
    private String address;
//    private Set<SuccursaleNEmployesDto> employes;
}
