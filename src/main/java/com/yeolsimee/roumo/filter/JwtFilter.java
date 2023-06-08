package com.yeolsimee.roumo.filter;

import com.google.firebase.auth.*;
import com.yeolsimee.roumo.app.common.response.*;
import com.yeolsimee.roumo.app.user.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.apache.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

import org.springframework.util.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String X_AUTH = "x-auth";

    private final FirebaseAuth firebaseAuth;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(X_AUTH);

        //테스트 환경에서 x-auth값에 username 적어서 바로 호출하기
        if (isProfileActive(System.getProperty("spring.profiles.active"))) {
            Authentication authentication = userService.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }

        if(!StringUtils.hasText(jwt)) {
            setUnauthorizedResponse(response, ResponseMessage.EMPTY_JWT_TOKEN.getMessage());
            return;
        }

        String uri = request.getRequestURI();

        if(uri.contains("/api/v1/login") || uri.contains("/api/v1/user/recovery")){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            FirebaseToken verifyIdToken = firebaseAuth.verifyIdToken(jwt);
            String uid = verifyIdToken.getUid();
            Authentication authentication = userService.getAuthentication(uid);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (FirebaseAuthException e) {
            log.error(e.getMessage());
        }

       filterChain.doFilter(request, response);

    }

    private void setUnauthorizedResponse(HttpServletResponse response, String message) throws IOException
    {
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write("{" +
                "\"success\": "+false+",\n" +
                "\"code\":"+CommonResponse.FAIL.getCode()+",\n" +
                "\"message\":\""+message+"\"\n" +
                "}");
    }

    private boolean isProfileActive(String activeProfile) {
        return (activeProfile == null || activeProfile.equals(""));
    }
}
