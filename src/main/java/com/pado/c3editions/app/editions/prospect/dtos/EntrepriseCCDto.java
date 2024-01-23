package com.pado.c3editions.app.editions.prospect.dtos;

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
public class EntrepriseCCDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String createdby;
    private String nom;
    private String address;
    private String url;
    private String tel;
    private String mail;

    public void setEntrepriseByContacts(Set<EntrepriseByContactDto> entrepriseByContacts) {
//        this.entrepriseByContacts = entrepriseByContacts;
        if(Objects.nonNull(entrepriseByContacts))
            contacts=entrepriseByContacts.stream().map(EntrepriseByContactDto::getContact).collect(Collectors.toSet());
    }

    private Set<EntrepriseByContactDto> entrepriseByContacts = new LinkedHashSet<>();
    private Set<ContactVisiteDto> contacts=new LinkedHashSet<>();

}
