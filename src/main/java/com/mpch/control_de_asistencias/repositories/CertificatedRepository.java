package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Certificated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CertificatedRepository extends JpaRepository<Certificated, UUID> {

    Optional<Certificated> findByIntern_User_Dni(String dni);

    @Query("SELECT c FROM Certificated c " +
            "WHERE LOWER(CONCAT(c.intern.user.name, ' ', c.intern.user.lastname)) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Certificated> findByInternFullName(@Param("fullName") String fullName, Pageable pageable);

    Page<Certificated> findByStatus(boolean status, Pageable pageable);

    void deleteAllByIntern_IdIntern(Long idIntern);

}
