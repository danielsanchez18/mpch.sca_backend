package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.AreaUniversity;
import com.mpch.control_de_asistencias.services.AreaUniversityService;
import com.mpch.control_de_asistencias.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/area-university")
public class AreaUniversityController {

    @Autowired
    private AreaUniversityService areaUniversityService;

    @PostMapping("/add")
    public ResponseEntity<?> addAreaUniversity(@RequestBody AreaUniversity areaUniversity) {
        try {
            AreaUniversity created = areaUniversityService.addAreaUniversity(areaUniversity);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Asociación creada exitosamente", created));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al asociar universidad con área"));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAreaUniversityById(@PathVariable UUID id) {
        try {
            AreaUniversity areaUniversity = areaUniversityService.getAreaUniversityById(id);
            return ResponseEntity.ok(ResponseUtils.successResponse("Relación encontrada", areaUniversity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAreaUniversities() {
        try {
            return ResponseEntity.ok(ResponseUtils.successResponse("Asociaciones obtenidas exitosamente", areaUniversityService.getAllAreaUniversities()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener las asociaciones"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAreaUniversities(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<AreaUniversity> areaUniversities = areaUniversityService.getAllAreaUniversities(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Asociaciones obtenidas exitosamente", areaUniversities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener asociaciones"));
        }
    }

    @GetMapping("/search/{areaName}/{universityName}")
    public ResponseEntity<?> searchByAreaOrUniversity(
            @PathVariable String areaName,
            @PathVariable String universityName,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<AreaUniversity> results = areaUniversityService.searchByAreaOrUniversity(areaName, universityName, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Resultados de búsqueda obtenidos", results));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar asociaciones"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAreaUniversity(@PathVariable UUID id) {
        try {
            areaUniversityService.deleteAreaUniversity(id);
            return ResponseEntity.ok(ResponseUtils.successResponse("Asociación eliminada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al eliminar la asociación"));
        }
    }

    @GetMapping("/university/{idUniversity}")
    public ResponseEntity<?> getAreasByUniversity(
            @PathVariable Long idUniversity,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<AreaUniversity> areas = areaUniversityService.getAreasByUniversity(idUniversity, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Áreas asociadas obtenidas exitosamente", areas));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener las áreas asociadas"));
        }
    }

    @GetMapping("/area/{idArea}")
    public ResponseEntity<?> getUniversitiesByArea(
            @PathVariable Long idArea,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        try {
            Page<AreaUniversity> universities = areaUniversityService.getUniversitiesByArea(idArea, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Universidades asociadas obtenidas exitosamente", universities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener las universidades asociadas"));
        }
    }

}
