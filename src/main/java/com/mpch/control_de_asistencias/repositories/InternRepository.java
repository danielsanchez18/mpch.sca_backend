package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Intern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface InternRepository extends JpaRepository<Intern, Long> {

    boolean existsByAreaUniversity_Area_IdArea(Long idArea);

    boolean existsByAreaUniversity_University_IdUniversity(Long idUniversity);

    boolean existsByAreaUniversity_IdAreaUniversity(UUID idAreaUniversity);

    @Query("SELECT i FROM Intern i " +
            "JOIN User u ON i.user.idUser = u.idUser " +
            "WHERE LOWER(CONCAT(u.name, ' ', u.lastname)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Intern> findByFullName(@Param("fullName") String fullName, Pageable pageable);

    Page<Intern> findByAreaUniversity_Area_NameContainingIgnoreCase(String name, Pageable pageable);

    Page<Intern> findByAreaUniversity_University_NameContainingIgnoreCase(String name, Pageable pageable);

    Page<Intern> findByAreaUniversity_IdAreaUniversity(UUID idAreaUniversity, Pageable pageable);

    long countByAreaUniversity_IdAreaUniversity(UUID idAreaUniversity);

    Optional<Intern> findByUser_Dni(String dni);

    @Query("SELECT i FROM Intern i " +
            "WHERE i.totalHours >= i.areaUniversity.hoursCertified")
    Page<Intern> findEligibleInterns(Pageable pageable);

    void deleteByUser_IdUser(UUID idUser);

}
