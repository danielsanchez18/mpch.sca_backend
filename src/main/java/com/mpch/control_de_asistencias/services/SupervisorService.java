package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.Supervisor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupervisorService {

    Supervisor saveSupervisor(Supervisor supervisor);

    Supervisor getSupervisorById(Long idSupervisor);

    List<Supervisor> getAllSupervisors();

    Page<Supervisor> getAllSupervisors(Pageable pageable);

    Page<Supervisor> searchSupervisorsByName(String name, Pageable pageable);

    Page<Supervisor> getSupervisorsByDni(String area, Pageable pageable);

    Page<Supervisor> getSupervisorsByArea(String area, Pageable pageable);

    Long getTotalSupervisors();

    Supervisor updateSupervisor(Long idSupervisor, Supervisor supervisor);

    void deleteSupervisor(Long idSupervisor);
}
