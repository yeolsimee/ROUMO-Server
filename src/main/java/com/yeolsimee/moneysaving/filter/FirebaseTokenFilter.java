package com.yeolsimee.moneysaving.filter;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.service.*;
import lombok.*;
import org.apache.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@RequiredArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        FirebaseToken decodedToken;
        String authToken = request.getHeader("x-auth");
        if (authToken == null) {
            setUnauthorizedResponse(response, "토큰값이 없습니다.");
            return;
        }

        try{
            decodedToken = firebaseAuth.verifyIdToken(authToken);
        } catch (FirebaseAuthException e) {
            setUnauthorizedResponse(response, "토큰 복호화에 실패했습니다.");
            return;
        }

        // User를 가져와 SecurityContext에 저장한다.
        try{
            UserDetails user = customUserDetailService.getUserByUid(decodedToken.getUid());
            if(user == null){
                customUserDetailService.signup(RegisterDto.builder().build());
                user = customUserDetailService.getUserByUid(decodedToken.getUid());
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch(NoSuchElementException e){
            setUnauthorizedResponse(response, "로그인 인증에 실패했습니다.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    //TODO 응답방식 변경 예정
    private void setUnauthorizedResponse(HttpServletResponse response, String code) throws IOException {

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }
}
