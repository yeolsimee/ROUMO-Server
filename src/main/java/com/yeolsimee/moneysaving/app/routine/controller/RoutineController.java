package com.yeolsimee.moneysaving.app.routine.controller;

import com.yeolsimee.moneysaving.app.common.response.service.ResponseService;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineDaysRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineResponse;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoutineController {

    private final ResponseService responseService;
    private final RoutineService routineService;

    @PostMapping("/routine")
    public ResponseEntity<?> createRoutine(@RequestBody RoutineRequest routineRequest){
        //임시 유저 아이디
        Long userId = 1L;
        RoutineResponse routineResponse = routineService.createRoutine(routineRequest, 1L);
        return ResponseEntity.ok(responseService.getSingleResult(routineResponse));
    }

    @GetMapping("/routinedays")
    public ResponseEntity<?> findAllMyRoutineDays( @RequestBody RoutineDaysRequest routineDaysRequest) {
        //임시 유저 아이디
        Long userId = 1L;
        return ResponseEntity.ok(responseService.getSingleResult(routineService.findRoutineDays(userId, routineDaysRequest)));
    }
}
