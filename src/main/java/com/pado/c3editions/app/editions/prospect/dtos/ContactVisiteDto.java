package com.pado.c3editions.app.editions.prospect.dtos;

import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.SEXE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactVisiteDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private SEXE sexe;
    private String mail;
    private String adresse;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String createdby;
    private CONTACT_TYPE contact_type = CONTACT_TYPE.CONTACT;
    private String type = "CONTACT";
    private String telephone;
    private Long succursale;
}
