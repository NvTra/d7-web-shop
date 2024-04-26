package com.tranv.d7shop.components;

import com.tranv.d7shop.exceptions.InvalidParamException;
import com.tranv.d7shop.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) {
        //properties -> claims
        Map<String, Object> claims = new HashMap<>();
//                this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration + 1000L))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)//phải chuyển secrecKey sang HS256
                    .compact();
            return token;
        } catch (Exception e) {
            throw new InvalidParamException(e.getMessage());
        }
    }

    // tạo secretkey tự động
    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        System.out.println(secretKey);
        return secretKey;
    }

    private Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
//        Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClams(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClams(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClams(token);
        return claimsResolver.apply(claims);
    }

    //check expiration
    public boolean isTokenExpiration(String token) {
        Date expirationDate = this.extractClams(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractPhoneNumber(String token) {
        return extractClams(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername())) && !isTokenExpiration(token);
    }
}
