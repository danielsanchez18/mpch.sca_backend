package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User save(User user);

    User findUserById(UUID idUser);

    List<User> findAllUsers();

    Page<User> findAllUsers(Pageable pageable);

    Page<User> searchUsersByName(String name, Pageable pageable);

    Page<User> findUsersByRole(Long idRole, Pageable pageable);

    Page<User> searchUsersByDni(String dni, Pageable pageable);

    Page<User> findUsersByStatus(boolean status, Pageable pageable);

    Long getTotalUsers();

    User updateUser(UUID idUser, User user);

    void deleteUser(UUID idUser);

}
