package com.pado.c3editions.app.editions.prospect.pojos.logs;

import com.pado.c3editions.app.editions.prospect.pojos.ImplModel;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GlobalLogs extends ImplModel {

	private String path;
	private String method;
}
