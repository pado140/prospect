package com.pado.c3editions.app.editions.prospect.pojos.logs;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.pado.c3editions.app.editions.prospect.pojos.ImplModel;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name="logs")
public class ContactLogs extends ImplModel{
	
	private String action;
	private String desciption;
	
	private Class clazz;
	@Column(name="user_id",nullable = true)
	private Long user;
	
}
