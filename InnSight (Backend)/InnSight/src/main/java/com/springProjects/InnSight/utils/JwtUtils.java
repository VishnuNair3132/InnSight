package com.springProjects.InnSight.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;




    @Service
    public class JwtUtils {
        String secretKey = "29fee550d6281a5daeeaaca835934c5e8e44675d74023f6ad70f8cc8d13a3b2e";


        private static final long EXPIRATION_TIME = 1000 * 60 * 24 * 7; //for 7 days

        private final SecretKey Key;

        public JwtUtils() {
            String secretString = "29fee550d6281a5daeeaaca835934c5e8e44675d74023f6ad70f8cc8d13a3b2e";
            byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
            this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");

        }


        public String generateToken(UserDetails userDetails) {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(Key)
                    .compact();
        }

        public String extractUsername(String token) {
            return extractClaims(token, Claims::getSubject);
        }

        private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
            return claimsTFunction.apply(
                    Jwts.parser()
                            .verifyWith(Key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload());
        }

        public boolean isValidToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }

        private boolean isTokenExpired(String token) {
            return extractClaims(token, Claims::getExpiration).before(new Date());
        }
    }
