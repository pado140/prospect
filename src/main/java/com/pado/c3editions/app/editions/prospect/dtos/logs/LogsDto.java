package com.pado.c3editions.app.editions.prospect.dtos.logs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsDto implements Serializable {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String action;
    private String desciption;
    private Class<?> clazz;
    private Long livre;
    private Long projet;
    private UserDto user;

    @Data
    public static class UserDto implements Serializable {
        private Long id;
        private LocalDateTime created;
        private LocalDateTime modified;
        private String username;
        private String password;
        private String recover;
        private boolean locked;
        private boolean disabled;
        private boolean expired;
        private boolean changepassword;
        private String permission;
    }
}
