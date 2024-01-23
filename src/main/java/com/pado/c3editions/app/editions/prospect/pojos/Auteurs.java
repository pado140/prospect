package com.pado.c3editions.app.editions.prospect.pojos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pado.c3editions.app.editions.prospect.pojos.ENUM.AUTEUR_TYPE;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity(name = "auteur")
@DiscriminatorValue(value = "AUTEUR")
public class Auteurs extends Contact {
	
	private String nom_plume;

	private String pob;//place of birth
	
	private LocalDate dob; //Date of birth
	
	private String profession;

	private AUTEUR_TYPE auteurType=AUTEUR_TYPE.PHYSIQUE;

	public Auteurs() {
		setType("AUTEUR");
	}

	@Transient
	public int age() {
		return (int) LocalDate.now().until(dob, ChronoUnit.YEARS);
	}

	@OneToOne(cascade = {CascadeType.ALL})
	@JsonIgnore
	@JoinColumn(name = "reference_id")
	private Contact reference;

}
