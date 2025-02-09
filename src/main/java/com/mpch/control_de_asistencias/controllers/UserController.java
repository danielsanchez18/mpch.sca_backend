package com.mpch.control_de_asistencias.controllers;

import com.mpch.control_de_asistencias.models.User;
import com.mpch.control_de_asistencias.services.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User createdUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtils.successResponse("Usuario creado exitosamente", createdUser));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/id/{idUser}")
    public ResponseEntity<?> getUserById(@PathVariable UUID idUser) {
        try {
            User user = userService.findUserById(idUser);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuario encontrado", user));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(ResponseUtils.successResponse("Lista de usuarios obtenida exitosamente", userService.findAllUsers()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener la lista de usuarios"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<User> users = userService.findAllUsers(pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuarios obtenidos", users));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al obtener usuarios"));
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> searchByFullName(@PathVariable String name, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<User> users = userService.searchUsersByName(name, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Búsqueda exitosa", users));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar usuarios"));
        }
    }

    @GetMapping("/role/{idRole}")
    public ResponseEntity<?> getUsersByRole(@PathVariable Long idRole, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<User> users = userService.findUsersByRole(idRole, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuarios por rol obtenidos", users));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar usuarios por rol"));
        }
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> searchByDni(@PathVariable String dni, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<User> users = userService.searchUsersByDni(dni, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuarios por dni obtenidos", users));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar usuarios"));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getUsersByStatus(@PathVariable boolean status, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<User> users = userService.findUsersByStatus(status, pageable);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuarios por estado obtenidos", users));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.errorResponse("Error al buscar usuarios por estado"));
        }
    }

    @GetMapping("/total")
    public Long getTotalUsers() {
        long totalUsers = userService.getTotalUsers();
        return totalUsers;
    }

    @PutMapping("/update/{idUser}")
    public ResponseEntity<?> updateUser(@PathVariable UUID idUser, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(idUser, user);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuario actualizado", updatedUser));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID idUser) {
        try {
            userService.deleteUser(idUser);
            return ResponseEntity.ok(ResponseUtils.successResponse("Usuario eliminado", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtils.errorResponse(ex.getMessage()));
        }
    }
}
