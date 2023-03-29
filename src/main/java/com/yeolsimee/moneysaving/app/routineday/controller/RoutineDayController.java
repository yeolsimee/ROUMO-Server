package com.yeolsimee.moneysaving.app.routineday.controller;

import com.yeolsimee.moneysaving.app.common.response.service.ResponseService;
import com.yeolsimee.moneysaving.app.routineday.dto.RoutineDaysRequest;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import com.yeolsimee.moneysaving.app.routineday.service.RoutineDayService;
import com.yeolsimee.moneysaving.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoutineDayController {

    private final ResponseService responseService;
    private final RoutineService routineService;
    private final RoutineDayService routineDayService;

    @GetMapping("/routinedays")
    public ResponseEntity<?> findAllMyRoutineDays(@RequestBody RoutineDaysRequest routineDaysRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseService.getSingleResult(routineService.findRoutineDays(user.getId(), routineDaysRequest)));
    }

    @GetMapping("/routineday/{pickDay}")
    public ResponseEntity<?> findMyRoutineDay(@PathVariable String pickDay, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseService.getSingleResult(routineDayService.findRoutineDay(user.getId(), pickDay)));
    }

    @PutMapping("/routinecheck/{routineDayId}")
    public ResponseEntity<?> updateRoutineCheck(@PathVariable String routineDayId, @RequestBody String routineCheckYN, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(responseService.getSingleResult(routineDayService.updateRoutineCheck(routineDayId, routineCheckYN)));
    }
}
