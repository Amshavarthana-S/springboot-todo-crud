package dev.codeio.HelloWorld.Utils;

import io.jsonwebtoken.JwtException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET="Amsha-springboot";
    private final long EXPIRATION=1000*60;
private final Key secretKey= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

public String generateToken(String email){
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
}
public boolean validateJwtToken(String token){
    try{
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
        return true;
    } catch (JwtException exception){
        return false;
    }
}
}
