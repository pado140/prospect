package com.pado.c3editions.app.editions.prospect.pojos;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CONTACT_TYPE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "contact")
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contact_class",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "CONTACT")
public class Contact extends Personnes{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
	private List<Visites> visites = new java.util.ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private CONTACT_TYPE contact_type=CONTACT_TYPE.CONTACT;

	@NotNull
	private String type="CONTACT";

	@Column(name="succursale_id")
	private Long succursale;

	private String folder;


	@OneToOne(mappedBy = "reference")
	private Auteurs auteur;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "contact")
	private List<EntrepriseByContact> entrepriseByContacts = new ArrayList<>();
}
