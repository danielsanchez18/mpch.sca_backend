package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UniversityService {

    University createUniversity(University university);

    University getUniversityById(Long idUniversity);

    List<University> getUniversities();

    Page<University> getAllUniversities(Pageable pageable);

    Page<University> searchUniversityByName(String name, Pageable pageable);

    Long getTotalUniversities();

    University updateUniversity(Long idUniversity, University university);

    void deleteUniversity(Long idUniversity);
}
