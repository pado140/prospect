package com.pado.c3editions.app.editions.prospect.pojos;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Entreprise extends ImplModel{

	private String nom;
	private String address;
	private String url;
	private String tel;
	private String mail;
//
//	@OneToMany(mappedBy = "entreprise")
//	private List<Contact> contacts;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "entreprise")
	private Set<EntrepriseByContact> entrepriseByContacts = new LinkedHashSet<>();
}
