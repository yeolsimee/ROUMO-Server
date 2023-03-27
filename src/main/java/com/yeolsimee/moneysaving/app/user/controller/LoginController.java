package com.yeolsimee.moneysaving.app.user.controller;

import com.yeolsimee.moneysaving.app.common.response.service.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

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
public class LoginController {

    private final ResponseService responseService;

    @PostMapping("/login")
    public ResponseEntity<?> login(){
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

}
