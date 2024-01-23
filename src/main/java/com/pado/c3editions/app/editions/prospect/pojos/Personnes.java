package com.pado.c3editions.app.editions.prospect.pojos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.pado.c3editions.app.editions.prospect.pojos.ENUM.SEXE;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class Personnes implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull(message = "Les nom sont obligatoires")
	private String nom;
	private String prenom;

	@Enumerated(EnumType.STRING)
	private SEXE sexe;
	
	private String telephone,mail,adresse,nif,niu;
	
	@CreationTimestamp
	private LocalDateTime created;
	
	@UpdateTimestamp
	private LocalDateTime modified;

	private String createdby;
	
}
