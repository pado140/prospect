package com.pado.c3editions.app.editions.prospect.dtos;

import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.SEXE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactEDto implements Serializable {
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

    public void setEntrepriseByContacts(Set<ContactByEntrepriseDto> entrepriseByContacts) {
//        this.entrepriseByContacts = entrepriseByContacts;
        if(Objects.nonNull(entrepriseByContacts))
            entreprises=entrepriseByContacts.stream().map(ContactByEntrepriseDto::getEntreprise).collect(Collectors.toSet());
    }

    private Set<EntrepriseByContactDto> entrepriseByContacts = new LinkedHashSet<>();
    private Set<EntrepriseCDto> entreprises=new LinkedHashSet<>();
}
