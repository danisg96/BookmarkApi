package com.segnalibri.api.Segnalibri.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET_KEY = "ygHdlwAdqgzsmrQDFQmHl398/HDI/dIUBM5F0qmFduwA1T4Jfen/6XGO/ty1a5My";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaim,
                                UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaim)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))// 1 ora
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimGetter) {
        Claims claims = extraxtAllClaims(token);
        return claimGetter.apply(claims);
    }

    private Claims extraxtAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBites = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBites);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails user) {
        return extractUserEmail(token).equals(user.getUsername()) && !isTokenExpired(token);
    }
}
