package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.University;
import com.mpch.control_de_asistencias.services.UniversityService;
import com.mpch.control_de_asistencias.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/university")
@CrossOrigin(origins = "http://localhost:4200")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @PostMapping("/add")
    public ResponseEntity<?> addUniversity(@RequestBody University university) {
        try {
            University createdUniversity = universityService.createUniversity(university);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Universidad creada exitosamente", createdUniversity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al crear la universidad"));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUniversityById(@PathVariable Long id) {
        try {
            University university = universityService.getUniversityById(id);
            return ResponseEntity.ok(ResponseUtils.successResponse("Universidad encontrada", university));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUniversities(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<University> universities = universityService.getAllUniversities(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Universidades obtenidas exitosamente", universities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener las universidades"));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> searchUniversityByName(@PathVariable String name, Pageable pageable) {
        try {
            Page<University> universities = universityService.searchUniversityByName(name, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Resultados de búsqueda obtenidos", universities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar las universidades"));
        }
    }

    @GetMapping("/total")
    public Long getTotalUniversities() {
        long totalUniversities = universityService.getTotalUniversities();
        return totalUniversities;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUniversity(@PathVariable Long id, @RequestBody University university) {
        try {
            University updatedUniversity = universityService.updateUniversity(id, university);
            return ResponseEntity.ok(ResponseUtils.successResponse("Universidad actualizada exitosamente", updatedUniversity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al actualizar la universidad"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long id) {
        try {
            universityService.deleteUniversity(id);
            return ResponseEntity.ok(ResponseUtils.successResponse("Universidad eliminada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al eliminar la universidad"));
        }
    }
}