package com.pado.c3editions.app.editions.prospect.pojos;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contact_entreprise")
@Builder
public class EntrepriseByContact extends ImplModel{
    @ManyToOne
    @JoinColumn(name = "entreprise_id")
    private Entreprise entreprise;
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
