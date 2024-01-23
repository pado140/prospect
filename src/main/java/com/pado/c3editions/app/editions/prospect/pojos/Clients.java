package com.pado.c3editions.app.editions.prospect.pojos;



import com.pado.c3editions.app.editions.prospect.pojos.ENUM.CLIENT_TYPE;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "client")
@DiscriminatorValue(value = "CLIENT")
public class Clients extends Contact {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	@Enumerated(EnumType.STRING)
	private CLIENT_TYPE ctype;
}
