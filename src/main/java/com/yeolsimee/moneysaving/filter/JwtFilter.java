package com.yeolsimee.moneysaving.filter;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.common.response.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import com.yeolsimee.moneysaving.app.user.service.*;
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
import java.util.*;

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

        if(uri.contains("/api/v1/login") || uri.contains("/api/v1/user/recovery")){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            FirebaseToken verifyIdToken = firebaseAuth.verifyIdToken(jwt);
            String uid = verifyIdToken.getUid();
            Authentication authentication = userService.getAuthentication(uid);
            User user = (User) authentication.getPrincipal();
            if(Objects.equals("Y", user.getDeleteYn())){
                setUnauthorizedResponse(response, ResponseMessage.WITHDRAW_USER.getMessage());
                return;
            }
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

}
