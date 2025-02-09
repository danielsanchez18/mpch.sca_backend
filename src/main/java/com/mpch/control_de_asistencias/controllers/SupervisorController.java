package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.Supervisor;
import com.mpch.control_de_asistencias.services.SupervisorService;
import com.mpch.control_de_asistencias.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @PostMapping("/add")
    public ResponseEntity<?> addSupervisor(@RequestBody Supervisor supervisor) {
        try {
            Supervisor createdSupervisor = supervisorService.saveSupervisor(supervisor);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Supervisor creado exitosamente", createdSupervisor));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/id/{idSupervisor}")
    public ResponseEntity<?> getSupervisorById(@PathVariable Long idSupervisor) {
        try {
            Supervisor supervisor = supervisorService.getSupervisorById(idSupervisor);
            return ResponseEntity.ok(ResponseUtils.successResponse("Supervisor encontrado", supervisor));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSupervisors() {
        try {
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de supervisores obtenida exitosamente", supervisorService.getAllSupervisors()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de supervisores"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSupervisors(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Supervisor> supervisors = supervisorService.getAllSupervisors(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de supervisores obtenida exitosamente", supervisors));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de supervisores"));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> searchSupervisorsByName(@PathVariable String name, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Supervisor> supervisors = supervisorService.searchSupervisorsByName(name, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Supervisores encontrados", supervisors));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los supervisores"));
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> searchSupervisorsByDni(@PathVariable String dni, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Supervisor> supervisors = supervisorService.getSupervisorsByDni(dni, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Supervisores encontrados", supervisors));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los supervisores"));
        }
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<?> searchSupervisorsByArea(@PathVariable String area, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Supervisor> supervisors = supervisorService.getSupervisorsByArea(area, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Supervisores encontrados", supervisors));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los supervisores"));
        }
    }

    @GetMapping("/total")
    public Long getTotalSupervisors() {
        long totalSupervisors = supervisorService.getTotalSupervisors();
        return totalSupervisors;
    }

    @PutMapping("/update/{idSupervisor}")
    public ResponseEntity<?> updateSupervisor(@PathVariable Long idSupervisor, @RequestBody Supervisor supervisor) {
        try {
            Supervisor updatedSupervisor = supervisorService.updateSupervisor(idSupervisor, supervisor);
            return ResponseEntity.ok(ResponseUtils.successResponse("Supervisor actualizado exitosamente", updatedSupervisor));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{idSupervisor}")
    public ResponseEntity<?> deleteSupervisor(@PathVariable Long idSupervisor) {
        try {
            supervisorService.deleteSupervisor(idSupervisor);
            return ResponseEntity.ok(ResponseUtils.successResponse("Supervisor eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }
}