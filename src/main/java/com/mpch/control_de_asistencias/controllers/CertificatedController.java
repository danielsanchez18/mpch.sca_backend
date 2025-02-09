package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.Certificated;
import com.mpch.control_de_asistencias.models.Intern;
import com.mpch.control_de_asistencias.services.CertificatedService;
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
@RequestMapping("/certificated")
public class CertificatedController {

    @Autowired
    private CertificatedService certificatedService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateCertificate(@RequestParam String dni) {
        try {
            Certificated certificated = certificatedService.generateCertificated(dni);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Certificado generado con éxito", certificated));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getCertificateById(@PathVariable UUID id) {
        try {
            Certificated certificated = certificatedService.findById(id);
            return ResponseEntity.ok(ResponseUtils.successResponse("Certificado encontrado", certificated));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/{dni}")
    public ResponseEntity<?> getCertificateByDni(@PathVariable String dni) {
        try {
            Certificated certificated = certificatedService.findCertificateByIntern(dni);
            return ResponseEntity.ok(ResponseUtils.successResponse("Certificado encontrado", certificated));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCertificates(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Certificated> certificates = certificatedService.findAllCertificates(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Certificados obtenidos con éxito", certificates));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener certificados"));
        }
    }

    @GetMapping("/eligible")
    public ResponseEntity<?> getEligibleInterns(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Intern> eligibleInterns = certificatedService.findEligibleInternsForCertification(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Practicantes elegibles obtenidos con éxito", eligibleInterns));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener practicantes elegibles"));
        }
    }
}
