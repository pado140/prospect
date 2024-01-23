package com.pado.c3editions.app.editions.prospect.middleware;

import com.pado.c3editions.app.editions.prospect.dtos.UsersDto;
import com.pado.c3editions.app.editions.prospect.dtos.logs.Logdto;
import com.pado.c3editions.app.editions.prospect.dtos.logs.LogsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserServiceClient {
    @Autowired
    private RestTemplate template;

    public UsersDto fetchUser(String username){
        return template.getForObject("http://AUTH/api/v1/users/username/"+username,UsersDto.class);
    }

    public LogsDto saveLog(Logdto log){
        return template.postForObject("http://AUTH/api/v1/users/log",log,LogsDto.class);
//        return template.getForObject("http://AUTH/api/v1/users/username/"+username,UsersDto.class);
    }
}
