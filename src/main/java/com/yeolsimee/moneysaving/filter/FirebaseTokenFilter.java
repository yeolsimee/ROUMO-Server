package com.yeolsimee.moneysaving.filter;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.entity.User;
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

    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //activeprofile이 없을 경우(test환경)에 작동
        if (isProfileActive(System.getProperty("spring.profiles.active"))) {
            UserDetails user = userService.getUserByUid(request.getHeader("uid"));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }

        FirebaseToken decodedToken;
        String authToken = request.getHeader("x-auth");
        if (authToken == null) {
            setUnauthorizedResponse(response, "토큰값이 없습니다.");
            return;
        }

        try{
            decodedToken = firebaseAuth.verifyIdToken(authToken);
        } catch (FirebaseAuthException e) {
            if(e.getAuthErrorCode() == AuthErrorCode.EXPIRED_ID_TOKEN) {
                setUnauthorizedResponse(response, "토큰시간이 만료되었습니다.");
            }else{
                setUnauthorizedResponse(response, "토큰 복호화에 실패했습니다.");
            }
            return;
        }

        // User를 가져와 SecurityContext에 저장한다.
        try{
            User user = userService.getUserByUid(decodedToken.getUid());
            if(user == null) {
                user = userService.signup(decodedToken);
                request.setAttribute("loginInfo", LoginDto.builder().name(user.getName()).isNewUser("Y").build());
            }else{
                request.setAttribute("loginInfo", LoginDto.builder().name(user.getName()).isNewUser("F").build());
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
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }

    private boolean isProfileActive(String activeProfile) {
        return (activeProfile == null || activeProfile.equals(""));
    }
}
