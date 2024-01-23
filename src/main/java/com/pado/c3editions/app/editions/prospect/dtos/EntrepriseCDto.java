package com.pado.c3editions.app.editions.prospect.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EntrepriseCDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String createdby;
    private String nom;
    private String address;
    private String url;
    private String tel;
    private String mail;
}
