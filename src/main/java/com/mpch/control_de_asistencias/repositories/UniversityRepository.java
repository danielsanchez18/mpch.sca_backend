package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    boolean existsByName(String name);

    Page<University> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
