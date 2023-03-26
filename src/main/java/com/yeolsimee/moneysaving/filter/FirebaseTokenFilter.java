package com.yeolsimee.moneysaving.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.yeolsimee.moneysaving.app.user.dto.RegisterDto;
import com.yeolsimee.moneysaving.app.user.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //activeprofile이 없을 경우(test환경)에 작동
        if (isProfileActive(System.getProperty("spring.profiles.active"))) {
            UserDetails user = customUserDetailService.getUserByUid(request.getHeader("uid"));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
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
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"code\":\""+code+"\"}");
    }

    private boolean isProfileActive(String activeProfile) {
        return (activeProfile == null || activeProfile.equals(""));
    }
}
