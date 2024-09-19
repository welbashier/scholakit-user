package com.gwais.sk_users.security.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import graphql.com.google.common.base.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil { 
	/* This class will handle JWT token creation and validation. */
	
	// Use a stronger secret key in production
    private final String SECRET_KEY = "443F762081FC8D433FF31552B78C6EE03B0AC0462406B3EDAD668979B5451E5C";
    //private final String SECRET_KEY_BASE64 = DatatypeConverter.printBase64Binary(SECRET_KEY.getBytes());
    //byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
    private final Integer EXPIRATION_HOURS = 2; 			// Token valid for this number of hours

    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // generate token from user details, but with empty claims
	public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }
    
	public String generateToken(Map<String, Object> claims, String subject) {
        long currentTimeMillis = System.currentTimeMillis();
		return Jwts
        		.builder()
        		.setClaims(claims)
        		.setSubject(subject)
        		.setIssuedAt  (new Date(currentTimeMillis))
        		.setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60 * EXPIRATION_HOURS)) 
        		.signWith(getSigningKey(), SignatureAlgorithm.HS256)
        		.compact();
    }
    
	private Claims extractAllClaims(String token) {
        return Jwts
        		.parserBuilder()
        		.setSigningKey(getSigningKey())
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
    }

	private Key getSigningKey() {
		byte[] bytesKey = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(bytesKey);
	}
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
