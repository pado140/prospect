package com.pado.c3editions.app.editions.prospect.pojos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "distributeur")
@Getter
@Setter
@DiscriminatorValue(value = "DISTRIBUTEUR")
public class Distributeurs extends Contact{
}
