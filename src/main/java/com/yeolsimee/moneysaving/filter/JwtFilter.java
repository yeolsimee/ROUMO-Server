package com.yeolsimee.moneysaving.filter;

import com.yeolsimee.moneysaving.config.jwt.*;
import lombok.*;
import org.apache.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.util.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String X_AUTH = "x-auth";

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(X_AUTH);

        if(!StringUtils.hasText(jwt)) {
            setUnauthorizedResponse(response, "토큰값이 없습니다.");
            return;
        }

        String uri = request.getRequestURI();

        if(uri.contains("/api/v1/login")){
            filterChain.doFilter(request, response);
            return;
        }else{
            if(tokenProvider.validateToken(jwt)){
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }

    //TODO 응답방식 변경 예정
    private void setUnauthorizedResponse(HttpServletResponse response, String code) throws IOException {

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }

    private boolean isProfileActive(String activeProfile) {
        return (activeProfile == null || activeProfile.equals(""));
    }
}
