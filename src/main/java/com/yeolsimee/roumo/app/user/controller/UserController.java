package com.yeolsimee.roumo.app.user.controller;

import com.google.firebase.auth.*;
import com.yeolsimee.roumo.app.common.response.service.*;
import com.yeolsimee.roumo.app.user.dto.*;
import com.yeolsimee.roumo.app.user.entity.*;
import com.yeolsimee.roumo.app.user.service.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

import java.util.*;

import static com.yeolsimee.roumo.filter.JwtFilter.X_AUTH;

/**
 * packageName    : com.yeolsimee.roumo.app.user.controller
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
    private final FirebaseAuth firebaseAuth;

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);

        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(jwt);
        String uid = firebaseToken.getUid();
        User user = userService.getUserByUid(uid);

        if(Objects.isNull(user)){
            user = userService.signUp(uid);
        }

        return ResponseEntity.ok(responseService.getSingleResult(UserInfoResponse.of(user)));
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

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user){
        userService.withdraw(user);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/user/recovery")
    public ResponseEntity<?> recovery(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);
        userService.recovery(jwt);
        return ResponseEntity.ok(responseService.getSuccessResult());

    }
}
