package com.yeolsimee.moneysaving.app.routine.controller;

import com.yeolsimee.moneysaving.app.common.response.service.ResponseService;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineRequest;
import com.yeolsimee.moneysaving.app.routine.dto.RoutineResponse;
import com.yeolsimee.moneysaving.app.routine.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
