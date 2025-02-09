package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.Security;
import com.mpch.control_de_asistencias.services.SecurityService;
import com.mpch.control_de_asistencias.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/add")
    public ResponseEntity<?> addSecurity(@RequestBody Security security) {
        try {
            Security createdSecurity = securityService.saveSecurity(security);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Seguridad creado exitosamente", createdSecurity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al crear el security"));
        }
    }

    @GetMapping("/id/{idSecurity}")
    public ResponseEntity<?> getSecurityById(@PathVariable Long idSecurity) {
        try {
            Security security = securityService.getSecurityById(idSecurity);
            return ResponseEntity.ok(ResponseUtils.successResponse("Seguridad encontrado", security));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSecurities() {
        try {
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de seguridades obtenida exitosamente", securityService.getAllSecurities()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de seguridades"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSecurities(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Security> securities = securityService.getAllSecurities(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de seguridades obtenida exitosamente", securities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de seguridades"));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> searchSecuritiesByName(@PathVariable String name, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Security> securities = securityService.searchSecuritiesByName(name, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Seguridades encontrados", securities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los seguridades"));
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> searchSecuritiesByDni(@PathVariable String dni, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Security> securities = securityService.searchSecuritiesByDni(dni, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Seguridades encontrados", securities));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los Seguridades"));
        }
    }

    @GetMapping("/total")
    public Long getTotalSecurities() {
        long totalSecurities = securityService.getTotalSecurities();
        return totalSecurities;
    }

    @PutMapping("/update/{idSecurity}")
    public ResponseEntity<?> updateSecurity(@PathVariable Long idSecurity, @RequestBody Security security) {
        try {
            Security updatedSecurity = securityService.updateSecurity(idSecurity, security);
            return ResponseEntity.ok(ResponseUtils.successResponse("Seguridad actualizado exitosamente", updatedSecurity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{idSecurity}")
    public ResponseEntity<?> deleteSecurity(@PathVariable Long idSecurity) {
        try {
            securityService.deleteSecurity(idSecurity);
            return ResponseEntity.ok(ResponseUtils.successResponse("Seguridad eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }
}