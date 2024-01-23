package com.pado.c3editions.app.editions.prospect.middleware;

import com.pado.c3editions.app.editions.prospect.dtos.UsersDto;
import com.pado.c3editions.app.editions.prospect.dtos.logs.Logdto;
import com.pado.c3editions.app.editions.prospect.dtos.logs.LogsDto;
import com.pado.c3editions.app.editions.prospect.dtos.newUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface UserClient {

    @PostExchange("auth/public/log")
    public LogsDto savelog(@RequestBody @Valid Logdto log);

    @PostExchange("auth/public/create")
    public UsersDto save(@RequestBody newUser user);

    @GetExchange("auth/public/get/{user_id}")
    public UsersDto getu(@PathVariable("user_id") long user_id);


    @GetExchange("auth/public/usernamewe/{username}")
    public UsersDto getwe(@PathVariable("username") String username);

}
