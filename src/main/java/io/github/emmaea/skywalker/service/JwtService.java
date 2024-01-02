package io.github.emmaea.skywalker.service;

import io.github.emmaea.skywalker.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${token.signingKey}")
    private String signingKey;

    @Value("${token.expirationTime}")
    private long expirationInMillis;

    @Value("${token.issuer}")
    private String issuer;

    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("isAdmin", false);
        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN.name()))) {
            claims.put("isAdmin", true);
        }
        return generateToken(claims, userDetails);
    }

    public String extractUsername(String token) {
       return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.compareTo(userDetails.getUsername()) == 0 && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims, T> resolveClaims) {
        Claims claims = extractAllClaims(token);
        return resolveClaims.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException je) {
            System.out.println(je.getMessage());
            return null;
        }
    }

    private String generateToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .signWith(getSigningKey())
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationInMillis))
                .compact();
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(signingKey.getBytes());
    }

}
