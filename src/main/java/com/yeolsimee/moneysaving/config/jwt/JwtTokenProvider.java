package com.yeolsimee.moneysaving.config.jwt;

import io.jsonwebtoken.security.SecurityException;
import lombok.*;
import com.google.auth.oauth2.*;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
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

    public String createToken(com.yeolsimee.moneysaving.app.user.entity.User user){

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

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    public boolean validateToken(String token) throws IllegalArgumentException {

        try{
            Jwts.parserBuilder().setSigningKey(privateKey).build().parseClaimsJws(token);
            return true;
        }catch (SecurityException | MalformedJwtException e1){
            e1.printStackTrace();
            log.error("잘못된 Jwt 서명입니다.");
        }catch (ExpiredJwtException e2){
            e2.printStackTrace();
            log.error("만료된 Jwt 서명입니다.");
        }catch (UnsupportedJwtException e3){
            e3.printStackTrace();
            log.error("지원되지 않는 Jwt 토큰입니다.");
        }catch (IllegalArgumentException e4){
            e4.printStackTrace();
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
