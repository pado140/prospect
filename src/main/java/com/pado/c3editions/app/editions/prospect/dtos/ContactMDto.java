package com.pado.c3editions.app.editions.prospect.dtos;

import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.SEXE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private SEXE sexe;
    private String telephone;
    private String mail;
    private String adresse;
    private String nif;
    private String niu;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String createdby;
    private CONTACT_TYPE contact_type = CONTACT_TYPE.CONTACT;
    private String type = "CONTACT";
    private Long succursale;
    private String folder;
    private Set<ContactByEntrepriseDto> entrepriseByContacts = new LinkedHashSet<>();
}
