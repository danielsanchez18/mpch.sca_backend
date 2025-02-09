package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Security;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Long> {

    @Query("SELECT s FROM Security s " +
            "JOIN User u ON s.user.idUser = u.idUser " +
            "WHERE LOWER(CONCAT(u.name, ' ', u.lastname)) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Security> findByFullName(@Param("fullName") String fullName, Pageable pageable);

    Page<Security> findByUser_DniContainingIgnoreCase(String dni, Pageable pageable);

    Optional<Security> findByUser_Dni(String dni);

}

