package com.yeolsimee.roumo.config.jwt;

import com.yeolsimee.roumo.app.user.entity.User;
import com.yeolsimee.roumo.app.user.service.*;
import lombok.*;
import com.google.auth.oauth2.*;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.io.IOException;
import java.security.*;
import java.util.*;
import java.util.stream.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private final UserService userService;

    @Value("${app.firebase-configuration-file}")
    private String firebaseSdkPath;

    private static final String AUTHORITIES_KEY = "auth";

    private static final long TOKEN_VALIDITY_IN_MILLISECONDS = 86400000;

    private PrivateKey privateKey;

    @Override
    public void afterPropertiesSet(){
        GoogleCredentials credentials = null;
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(firebaseSdkPath)) {
            credentials = GoogleCredentials.fromStream(Objects.requireNonNull(resourceAsStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        privateKey = ((ServiceAccountCredentials) Objects.requireNonNull(credentials)).getPrivateKey();

    }

    public String createToken(User user){

        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + TOKEN_VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(privateKey)
                .setExpiration(validity)
                .compact();
    }

    public String getUid(String accessToken){

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(privateKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        return claims.get("uid", String.class);

    }

    public Authentication getAuthentication(String accessToken){

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(privateKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        User user = userService.getUserByUid(claims.getSubject().toString());

        return new UsernamePasswordAuthenticationToken(user, accessToken, user.getAuthorities());
    }

    public boolean validateToken(String token){
            Jwts.parserBuilder().setSigningKey(privateKey).build().parseClaimsJws(token);
            return true;
    }

}
