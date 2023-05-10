package com.yeolsimee.moneysaving.app.user.controller;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.common.response.service.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import com.yeolsimee.moneysaving.app.user.service.*;
import com.yeolsimee.moneysaving.config.jwt.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.*;
import lombok.extern.slf4j.*;
import org.apache.http.HttpStatus;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

import static com.google.firebase.auth.AuthErrorCode.EXPIRED_ID_TOKEN;
import static com.yeolsimee.moneysaving.filter.JwtFilter.X_AUTH;

/**
 * packageName    : com.yeolsimee.moneysaving.app.user.controller
 * fileName       : LoginController
 * author         : jeon-eunseong
 * date           : 2023/03/01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/03/01        jeon-eunseong       최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final ResponseService responseService;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final FirebaseAuth firebaseAuth;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);
        String uid = "";
        try {
            FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(jwt);
            uid = firebaseToken.getUid();
        } catch (FirebaseAuthException e) {
            String errorMsg = "";
            switch (e.getAuthErrorCode()) {
                case EXPIRED_ID_TOKEN -> errorMsg = "토큰이 만료되었습니다.";
                case INVALID_ID_TOKEN -> errorMsg = "토큰이 잘못되었습니다.";
            }
            return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body(responseService.getFailResult(errorMsg));
        }
        User user = userService.getUserByUid(uid);
        if(user == null){
            user = userService.signUp(uid);
        }
        String token = tokenProvider.createToken(user);
        return ResponseEntity.ok(responseService.getSingleResult(UserInfoResponse.of(user, token)));
    }

    @PostMapping("/login/custom")
    public ResponseEntity customLogin(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);
        UserInfoResponse result = null;
        try {
            if(tokenProvider.validateToken(jwt)) {
                String uid = tokenProvider.getUid(jwt);
                User user = userService.getUserByUid(uid);
                if (user == null) {
                    user = userService.signUp(uid);
                }
                String token = tokenProvider.createToken(user);
                result = UserInfoResponse.of(user, token);
            }
        }catch (SecurityException | MalformedJwtException e1){
            throw new SecurityException("잘못된 Jwt 서명입니다.");
        }catch (ExpiredJwtException e2){
            log.error("만료된 Jwt 서명입니다.");
            throw new ExpiredJwtException(e2.getHeader(), e2.getClaims(), e2.getMessage());
        }catch (UnsupportedJwtException e3){
            log.error("지원되지 않는 Jwt 토큰입니다.");
            throw new UnsupportedJwtException("지원되지 않는 Jwt 토큰입니다.");
        }catch (IllegalArgumentException e4){
            log.error("JWT 토큰이 잘못되었습니다.");
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
        }
        return ResponseEntity.ok(responseService.getSingleResult(result));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getSingleResult(UserInfoResponse.of(user)));
    }

    @PostMapping("/user")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @RequestBody UserInfoRequest userInfoRequest){
        userService.updateUserInfo(userInfoRequest, user);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/isnewuser/update")
    public ResponseEntity<?> updateIsNewUser(@AuthenticationPrincipal User user, @RequestBody UserInfoRequest userInfoRequest) {
        UserInfoResponse userInfoResponse = userService.updateIsNewUser(user, userInfoRequest);
        return ResponseEntity.ok(responseService.getSingleResult(userInfoResponse));
    }
}
