package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Page<Admin> findByUser_DniContainingIgnoreCase(String dni, Pageable pageable);

    Optional<Admin> findByUser_Dni(String dni);

    @Query(value = "SELECT a FROM Admin a " +
            "JOIN User u ON a.user.idUser = u.idUser " +
            "WHERE LOWER(CONCAT(u.name, ' ', u.lastname)) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Admin> findByFullName(@Param("fullName") String fullName, Pageable pageable);

}
