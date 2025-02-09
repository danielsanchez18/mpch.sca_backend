package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.Assistance;
import com.mpch.control_de_asistencias.services.AssistanceService;
import com.mpch.control_de_asistencias.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/assistance")
public class AssistanceController {

    @Autowired
    private AssistanceService assistanceService;

    @PostMapping("/check-in")
    public ResponseEntity<?> registerCheckIn(@RequestParam String dni) {
        try {
            Assistance assistance = assistanceService.registerCheckIn(dni);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Check-In registrado con éxito", assistance));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> registerCheckOut(@RequestParam String dni) {
        try {
            Assistance assistance = assistanceService.registerCheckOut(dni);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Check-Out registrado con éxito", assistance));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> getAssistancesByDate(
            @RequestParam LocalDate date,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<Assistance> assistances = assistanceService.getAssistancesByDate(date, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Asistencias obtenidas con éxito", assistances));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener asistencias por fecha"));
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<?> getAssistancesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<Assistance> assistances = assistanceService.getAssistancesByDateRange(startDate, endDate, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Asistencias obtenidas con éxito", assistances));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener asistencias por rango de fechas"));
        }
    }

    @GetMapping("/intern-name")
    public ResponseEntity<?> searchAssistancesByInternName(
            @RequestParam String name,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<Assistance> assistances = assistanceService.searchAssistancesByInternName(name, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Asistencias obtenidas con éxito", assistances));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar asistencias por nombre del practicante"));
        }
    }

    @GetMapping("/area")
    public ResponseEntity<?> findAssistancesByArea(
            @RequestParam String areaName,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<Assistance> assistances = assistanceService.findAssistancesByArea(areaName, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Asistencias obtenidas por área con éxito", assistances));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar asistencias por área"));
        }
    }

    @GetMapping("/hours-by-date")
    public ResponseEntity<?> getHoursWorkedByInternOnDate(
            @RequestParam String dni,
            @RequestParam LocalDate date
    ) {
        try {
            double hours = assistanceService.getHoursWorkedByInternOnDate(dni, date);
            return ResponseEntity.ok(ResponseUtils.successResponse("Horas trabajadas obtenidas con éxito", hours));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/hours-by-area")
    public ResponseEntity<?> getMonthlyHoursWorkedByArea(@RequestParam String areaName,
                                                         @RequestParam int month,
                                                         @RequestParam int year) {
        try {
            double hours = assistanceService.getMonthlyHoursWorkedByArea(areaName, month, year);
            return ResponseEntity.ok(ResponseUtils.successResponse("Horas mensuales trabajadas por área obtenidas con éxito", hours));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener horas trabajadas por área"));
        }
    }

    @GetMapping("/hours-by-university")
    public ResponseEntity<?> getMonthlyHoursWorkedByUniversity(@RequestParam String universityName,
                                                               @RequestParam int month,
                                                               @RequestParam int year) {
        try {
            double hours = assistanceService.getMonthlyHoursWorkedByUniversity(universityName, month, year);
            return ResponseEntity.ok(ResponseUtils.successResponse("Horas mensuales trabajadas por universidad obtenidas con éxito", hours));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener horas trabajadas por universidad"));
        }
    }

}

