package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByRole_IdRole(Long idRole);

    boolean existsByDni(String dni);

    @Query("SELECT u FROM User u " +
            "WHERE LOWER(CONCAT(u.name, ' ', u.lastname)) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<User> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName, Pageable pageable);

    Page<User> findByRole_IdRole(Long idRole, Pageable pageable);

    Page<User> findByDniContainingIgnoreCase(String dni, Pageable pageable);

    Page<User> findByStatus(boolean status, Pageable pageable);

}
