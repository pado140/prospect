package com.pado.c3editions.app.editions.prospect.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pado.c3editions.app.editions.prospect.config.JWTHelper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiFilter extends OncePerRequestFilter {

//    @Autowired
//    private JWTHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "86400");
//        response.setHeader("Content-Type", "Application/json");
//        System.out.println(authorization);
        if(request.getMethod().equalsIgnoreCase("options"))
            return;
        if(Strings.isBlank(authorization)||!authorization.startsWith("Bearer ")){
            System.out.println("login");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(request,response);
            return;
        }

        String token=authorization.split(" ")[1];
        try{

            String username= JWTHelper.extractUsername(token);
            String[]  Authorities=JWTHelper.extractClaim(token);
            List<GrantedAuthority> autorities= Arrays.stream(Authorities).map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                    username,null,autorities
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request,response);
        }catch ( Exception exception){
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        	if(exception instanceof ExpiredJwtException)
                response.setStatus(498);


            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("error_message", exception.getMessage()));
            exception.printStackTrace();
//            System.out.println(exception.getCause().toString());
        }
    }
}
