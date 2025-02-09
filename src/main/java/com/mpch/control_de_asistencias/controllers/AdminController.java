package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.Admin;
import com.mpch.control_de_asistencias.services.AdminService;
import com.mpch.control_de_asistencias.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/add")
    public ResponseEntity<?> addAdmin(@RequestBody Admin admin) {
        try {
            Admin createdAdmin = adminService.saveAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Administrador creado exitosamente", createdAdmin));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al crear el administrador"));
        }
    }

    @GetMapping("/id/{idAdmin}")
    public ResponseEntity<?> getAdminById(@PathVariable Long idAdmin) {
        try {
            Admin admin = adminService.getAdminById(idAdmin);
            return ResponseEntity.ok(ResponseUtils.successResponse("Administrador encontrado", admin));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAdmins() {
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de administradores obtenida exitosamente", admins));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de administradores"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdminsPage(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Admin> admins = adminService.getAllAdmins(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de administradores obtenida exitosamente", admins));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de administradores"));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> searchAdminsByName(@PathVariable String name, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Admin> admins = adminService.searchAdminsByName(name, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Administradores encontrados", admins));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los administradores"));
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> searchAdminsByDni(@PathVariable String dni, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<Admin> admins = adminService.searchAdminsByDni(dni, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Administradores encontrados", admins));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar los administradores"));
        }
    }

    @GetMapping("/total")
    public Long getTotalAdmins() {
        return adminService.getTotalAdmins();
    }

    @PutMapping("/update/{idAdmin}")
    public ResponseEntity<?> updateAdmin(@PathVariable Long idAdmin, @RequestBody Admin admin) {
        try {
            Admin updatedAdmin = adminService.updateAdmin(idAdmin, admin);
            return ResponseEntity.ok(ResponseUtils.successResponse("Administrador actualizado exitosamente", updatedAdmin));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{idAdmin}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long idAdmin) {
        try {
            adminService.deleteAdmin(idAdmin);
            return ResponseEntity.ok(ResponseUtils.successResponse("Administrador eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }
}