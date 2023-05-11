package com.yeolsimee.moneysaving.filter;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.common.response.*;
import com.yeolsimee.moneysaving.app.user.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.apache.http.*;
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

        if(!StringUtils.hasText(jwt)) {
            setUnauthorizedResponse(response, ResponseMessage.EMPTY_JWT_TOKEN.getMessage());
            return;
        }

        String uri = request.getRequestURI();

        if(uri.contains("/api/v1/login")){
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

    //TODO 응답방식 변경 예정
    private void setUnauthorizedResponse(HttpServletResponse response, String code) throws IOException {

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }

}
