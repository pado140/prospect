package com.pado.c3editions.app.editions.prospect.config;

import com.pado.c3editions.app.editions.prospect.middleware.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RegisterClient {

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;
    @Bean
    public WebClient UserWebClient(){
        return WebClient
                .builder()
                .baseUrl("lb://auth")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public UserClient userClient(){
        HttpServiceProxyFactory httpServiceProxyFactory=
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(UserWebClient())).build();
        return httpServiceProxyFactory.createClient(UserClient.class);
    }
}
