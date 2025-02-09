package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.Intern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InternService {

    Intern saveIntern(Intern intern);

    Intern findInternById(Long idIntern);

    List<Intern> getAllInterns();

    Page<Intern> findAllInterns(Pageable pageable);

    Page<Intern> searchInternsByName(String name, Pageable pageable);

    Page<Intern> findInternsByArea(String area, Pageable pageable);

    Page<Intern> findInternsByUniversity(String university, Pageable pageable);

    Page<Intern> findInternsByAreaUniversity(UUID idAreaUniversity, Pageable pageable);

    Long getTotalInterns();

    Intern updateIntern(Long idIntern, Intern intern);

    void deleteIntern(Long idIntern);

}
