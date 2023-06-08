package com.yeolsimee.roumo.app.routinehistory.controller;

import com.yeolsimee.roumo.app.common.response.service.ResponseService;
import com.yeolsimee.roumo.app.routinehistory.dto.RoutineCheckRequest;
import com.yeolsimee.roumo.app.routinehistory.service.RoutineHistoryService;
import com.yeolsimee.roumo.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoutineHistoryController {

    private final ResponseService responseService;
    private final RoutineHistoryService routineHistoryService;

    @PostMapping("/routinecheck")
    public ResponseEntity<?> createRoutineCheck(@RequestBody RoutineCheckRequest routineCheckRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseService.getSingleResult(routineHistoryService.changeOrCreateRoutineCheck(user.getId(), routineCheckRequest)));
    }
}
