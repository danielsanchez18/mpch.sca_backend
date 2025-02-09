package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.Role;
import com.mpch.control_de_asistencias.models.Supervisor;
import com.mpch.control_de_asistencias.models.User;
import com.mpch.control_de_asistencias.repositories.RoleRepository;
import com.mpch.control_de_asistencias.repositories.SupervisorRepository;
import com.mpch.control_de_asistencias.services.SupervisorService;
import com.mpch.control_de_asistencias.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisorServiceImpl implements SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    /* @Autowired
    private BCryptPasswordEncoder passwordEncoder; */

    @Override
    public Supervisor saveSupervisor(Supervisor supervisor) {

        // Validar que no exista ya un supervisor en el área
        if (supervisorRepository.existsByArea_IdArea(supervisor.getArea().getIdArea())) {
            throw new RuntimeException("El área ya tiene un supervisor asignado.");
        }

        Role supervisorRole = roleRepository.findById(2L).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setIdRole(2L);
            newRole.setName("supervisor");
            return roleRepository.save(newRole);
        });

        supervisor.getUser().setRole(supervisorRole);

        if (supervisor.getUser().getIdUser() == null) {
            User savedUser = userService.save(supervisor.getUser());
            supervisor.setUser(savedUser);
        }

        // supervisor.setPassword(passwordEncoder.encode(supervisor.getPassword()));

        return supervisorRepository.save(supervisor);
    }

    @Override
    public Supervisor getSupervisorById(Long idSupervisor) {
        return supervisorRepository.findById(idSupervisor)
                .orElseThrow(() -> new RuntimeException("Supervisor no encontrado."));
    }

    @Override
    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();
    }

    @Override
    public Page<Supervisor> getAllSupervisors(Pageable pageable) {
        return supervisorRepository.findAll(pageable);
    }

    @Override
    public Page<Supervisor> searchSupervisorsByName(String name, Pageable pageable) {
        return supervisorRepository.findByFullName(name, pageable);
    }

    @Override
    public Page<Supervisor> getSupervisorsByDni(String area, Pageable pageable) {
        return supervisorRepository.findByUser_DniContainingIgnoreCase(area, pageable);
    }

    @Override
    public Page<Supervisor> getSupervisorsByArea(String area, Pageable pageable) {
        return supervisorRepository.findByArea_NameContainingIgnoreCase(area, pageable);
    }

    @Override
    public Long getTotalSupervisors() {
        return supervisorRepository.count();
    }

    @Override
    public Supervisor updateSupervisor(Long idSupervisor, Supervisor supervisor) {

        Supervisor existingSupervisor = supervisorRepository.findById(idSupervisor).orElseThrow(
                () -> new RuntimeException("No se encontró el supervisor.")
        );

        boolean supervisorExistsInArea = supervisorRepository.existsByArea_IdArea(supervisor.getArea().getIdArea());
        if (supervisorExistsInArea && !existingSupervisor.getArea().getIdArea().equals(supervisor.getArea().getIdArea())) {
            throw new RuntimeException("El área ya tiene un supervisor asignado.");
        }

        User updatedUser = userService.updateUser(existingSupervisor.getUser().getIdUser(), supervisor.getUser());
        existingSupervisor.setUser(updatedUser);

        existingSupervisor.setArea(supervisor.getArea());
        existingSupervisor.setPassword(supervisor.getPassword());

        return supervisorRepository.save(existingSupervisor);
    }

    @Override
    public void deleteSupervisor(Long idSupervisor) {

        Supervisor supervisor = supervisorRepository.findById(idSupervisor).orElseThrow(
                () -> new RuntimeException("No se encontró el supervisor.")
        );

        supervisorRepository.deleteById(idSupervisor);
        userService.deleteUser(supervisor.getUser().getIdUser());
    }
}
