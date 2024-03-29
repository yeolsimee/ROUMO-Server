package com.yeolsimee.roumo.app.routine.controller;

import com.yeolsimee.roumo.app.common.response.service.ResponseService;
import com.yeolsimee.roumo.app.routine.dto.RoutineRequest;
import com.yeolsimee.roumo.app.routine.dto.RoutineResponse;
import com.yeolsimee.roumo.app.routine.service.RoutineService;
import com.yeolsimee.roumo.app.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoutineController {

    private final ResponseService responseService;
    private final RoutineService routineService;

    @PostMapping("/routine")
    public ResponseEntity<?> createRoutine(@AuthenticationPrincipal User user, @RequestBody RoutineRequest routineRequest) {
        RoutineResponse routineResponse = routineService.createRoutine(routineRequest, user.getId());
        return ResponseEntity.ok(responseService.getSingleResult(routineResponse));
    }

    @GetMapping("/routine/{routineId}")
    public ResponseEntity<?> getRoutine(@AuthenticationPrincipal User user, @PathVariable Long routineId){
        RoutineResponse routineResponse = routineService.findRoutineByUserIdAndRoutineId(user.getId(), routineId);
        return ResponseEntity.ok(responseService.getSingleResult(routineResponse));
    }

    @GetMapping("/routine")
    public ResponseEntity<?> getRoutines(@AuthenticationPrincipal User user){
        List<RoutineResponse> routineResponses = routineService.findRoutinesByUserId(user.getId());
        return ResponseEntity.ok(responseService.getListResult(routineResponses));
    }

    @PutMapping("/routine/{routineId}")
    public ResponseEntity<?> updateRoutine(@AuthenticationPrincipal User user, @PathVariable Long routineId, @RequestBody RoutineRequest routineRequest) {
        RoutineResponse routineResponse = routineService.updateRoutine(routineRequest, user.getId(), routineId);
        return ResponseEntity.ok(responseService.getSingleResult(routineResponse));
    }

    @DeleteMapping("/routine/{routineId}")
    public ResponseEntity<?> deleteRoutine(@AuthenticationPrincipal User user, @PathVariable Long routineId){
        routineService.deleteRoutine(user.getId(), routineId);
        return ResponseEntity.ok(responseService.getSuccessResult("루틴이 삭제되었습니다."));
    }

    @GetMapping("/routinedays")
    public ResponseEntity<?> findAllMyRoutineDays(@AuthenticationPrincipal User user, @RequestParam String startDate, @RequestParam String endDate) {
        return ResponseEntity.ok(responseService.getSingleResult(routineService.findRoutineDays(user.getId(), startDate, endDate)));
    }

    @GetMapping("/routineday")
    public ResponseEntity<?> findMyRoutineDay(@AuthenticationPrincipal User user, @RequestParam String date, @RequestParam String checkedRoutineShow) {
        return ResponseEntity.ok(responseService.getSingleResult(routineService.findRoutineDay(user.getId(), date, checkedRoutineShow)));
    }
}
