package com.pado.c3editions.app.editions.prospect.pojos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "demarcheur")
@DiscriminatorValue(value = "DEMARCHEUR")
public class Demarcheurs extends Contact {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String departement;

}
