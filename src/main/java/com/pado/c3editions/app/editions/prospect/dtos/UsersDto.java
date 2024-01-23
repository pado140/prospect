package com.pado.c3editions.app.editions.prospect.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String username;
    private String password;
    private String recover;
    private boolean locked;
    private boolean disabled;
    private boolean expired;
    private boolean changepassword = true;
    private EmployesDto employe;
    private String permission;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployesDto implements Serializable {
        private Long id;
        @NotNull(message = "Les nom et prenom sont obligatoires")
        private String nom;
        @NotNull(message = "Les nom et prenom sont obligatoires")
        private String prenom;
        private String mail;
        private String adresse;
        private LocalDateTime created;
        private LocalDateTime modified;
        @NotNull(message = "Le nif doit etre saisi")
        private LocalDate naissance;
        private String nif;
        @NotBlank(message = "Le numero unique doit etre saisi")
        @NotEmpty(message = "Le numero unique doit etre saisi")
        @NotNull(message = "Le numero unique doit etre saisi")
        private String niu;
        private String telephone;
        private String photo;
        private SuccursaleDto succursale;
    }
}
