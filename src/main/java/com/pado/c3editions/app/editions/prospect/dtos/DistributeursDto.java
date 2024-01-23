package com.pado.c3editions.app.editions.prospect.dtos;

import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.SEXE;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DistributeursDto implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private SEXE sexe;
    private String mail;
    private String adresse;
    private LocalDateTime created;
    private LocalDateTime modified;
//    private EntrepriseDto entreprise;
    private CONTACT_TYPE contact_type;
    private String type;
    private String telephone;
}
