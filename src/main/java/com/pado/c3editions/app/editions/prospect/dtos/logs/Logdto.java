package com.pado.c3editions.app.editions.prospect.dtos.logs;

import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Logdto {
    private Long id;
    private String user,message,action, claz;
    private Map<String,?> payload;
}
