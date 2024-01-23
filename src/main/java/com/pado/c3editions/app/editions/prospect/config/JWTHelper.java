package com.pado.c3editions.app.editions.prospect.config;

import java.security.Key;
import java.util.Date;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;

public class JWTHelper {

	public static String secretKey="357538782F413F4428472B4B6250655367566B59703373367639792442264529";
//	public static Ed25519.Algorithm algorithm= Ed25519.Algorithm.HMAC256(secretKey.getBytes());
//
	public static String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public static Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	public static String[] extractClaim(String token) {
		return extractAllClaims(token).get("Authorities",String[].class);
	}
	private static Claims extractAllClaims(String token) {
//		Jwts.
		return Jwts.parserBuilder().deserializeJsonWith(new JacksonDeserializer(Maps.of("Authorities",String[].class).build())).setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

	}

	public static Boolean isTokenExpired(String token) {
		return extractExpiration(token).after(new Date());
	}

	public static Boolean validateToken(String token) {
		final String username = extractUsername(token);
		return (username!=null && !isTokenExpired(token));
	}

	private static Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
