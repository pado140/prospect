package com.pado.c3editions.app.editions.prospect.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitesDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String createdby;
    private String visite;
    private LocalDate date_visite;
}
