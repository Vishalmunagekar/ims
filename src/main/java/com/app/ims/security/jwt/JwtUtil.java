package com.app.ims.security.jwt;

import com.app.ims.common.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Configuration
public class JwtUtil  implements Serializable {

    public String generateToken(Authentication authentication, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim(Constants.AUTHORITIES_KEY, authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.TokenExpiration))
                .compact();
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(Constants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
