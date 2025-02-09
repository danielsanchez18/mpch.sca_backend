package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.AreaUniversity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AreaUniversityService {

    AreaUniversity addAreaUniversity(AreaUniversity areaUniversity);

    AreaUniversity getAreaUniversityById(UUID idAreaUniversity);

    List<AreaUniversity> getAllAreaUniversities();

    Page<AreaUniversity> getAllAreaUniversities(Pageable pageable);

    Page<AreaUniversity> searchByAreaOrUniversity(String areaName, String nameUniversity, Pageable pageable);

    void deleteAreaUniversity(UUID idAreaUniversity);

    Page<AreaUniversity> getAreasByUniversity(Long idUniversity, Pageable pageable);

    Page<AreaUniversity> getUniversitiesByArea(Long idArea, Pageable pageable);

}
