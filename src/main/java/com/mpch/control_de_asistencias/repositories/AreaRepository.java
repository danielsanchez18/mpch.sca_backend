package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    boolean existsByName(String name);

    Optional<Area> findByName (String name);

    Page<Area> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
