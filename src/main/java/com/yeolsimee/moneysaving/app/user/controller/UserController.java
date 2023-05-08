package com.yeolsimee.moneysaving.app.user.controller;

import com.google.firebase.auth.*;
import com.yeolsimee.moneysaving.app.common.response.service.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import com.yeolsimee.moneysaving.app.user.service.*;
import com.yeolsimee.moneysaving.config.jwt.*;
import lombok.*;
import org.springframework.core.env.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

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
            throw new RuntimeException(e);
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
        if(tokenProvider.validateToken(jwt)) {
            String uid = tokenProvider.getUid(jwt);
            User user = userService.getUserByUid(uid);
            if (user == null) {
                user = userService.signUp(uid);
            }
            String token = tokenProvider.createToken(user);
            result = UserInfoResponse.of(user, token);
        }
        return ResponseEntity.ok(responseService.getSingleResult(result));

    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> userInfo(HttpServletRequest request){
        User user = userService.getUserByUid(request.getHeader(X_AUTH));
        return ResponseEntity.ok(responseService.getSingleResult(user));
    }

    @PostMapping("/userInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfoRequest userInfoRequest){
        userService.updateUserInfo(userInfoRequest);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/isnewuser/update")
    public ResponseEntity<?> updateIsNewUser(@AuthenticationPrincipal User user, @RequestBody UserInfoRequest userInfoRequest) {
        UserInfoResponse userInfoResponse = userService.updateIsNewUser(user, userInfoRequest);
        return ResponseEntity.ok(responseService.getSingleResult(userInfoResponse));
    }
}
