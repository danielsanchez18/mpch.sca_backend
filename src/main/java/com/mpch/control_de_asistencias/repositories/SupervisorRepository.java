package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Supervisor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {

    boolean existsByArea_IdArea(Long idArea);

    @Query("SELECT s FROM Supervisor s " +
            "JOIN User u ON s.user.idUser = u.idUser " +
            "WHERE LOWER(CONCAT(u.name, ' ', u.lastname)) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Supervisor> findByFullName(@Param("fullName") String fullName, Pageable pageable);

    Page<Supervisor> findByUser_DniContainingIgnoreCase(String dni, Pageable pageable);

    Page<Supervisor> findByArea_NameContainingIgnoreCase(String area, Pageable pageable);

    Optional<Supervisor> findByUser_Dni(String dni);

}
