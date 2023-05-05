package com.yeolsimee.moneysaving.app.user.controller;

import com.yeolsimee.moneysaving.app.common.response.service.*;
import com.yeolsimee.moneysaving.app.user.dto.*;
import com.yeolsimee.moneysaving.app.user.entity.*;
import com.yeolsimee.moneysaving.app.user.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(responseService.getSingleResult(UserInfoResponse.of(user)));
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> userInfo(HttpServletRequest request){
        User user = userService.getUserByUid(request.getHeader("x-auth"));
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
