package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.AreaUniversity;
import com.mpch.control_de_asistencias.models.Intern;
import com.mpch.control_de_asistencias.models.Role;
import com.mpch.control_de_asistencias.models.User;
import com.mpch.control_de_asistencias.repositories.*;
import com.mpch.control_de_asistencias.services.InternService;
import com.mpch.control_de_asistencias.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InternServiceImpl implements InternService {

    @Autowired
    private InternRepository internRepository;

    @Autowired
    private AssistanceRepository assistanceRepository;

    @Autowired
    private AreaUniversityRepository areaUniversityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CertificatedRepository certificatedRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Validar que existan vacantes en el área requerida
    private void validateVacancies(UUID idAreaUniversity) {
        AreaUniversity areaUniversity = areaUniversityRepository.findById(idAreaUniversity).orElseThrow(
                () -> new RuntimeException("No se encontró el área-universidad.")
        );
        long currentInterns = internRepository.countByAreaUniversity_IdAreaUniversity(idAreaUniversity);
        if (currentInterns >= areaUniversity.getArea().getNroVacancies()) {
            throw new RuntimeException("El área ya alcanzó el número máximo de vacantes.");
        }
    }

    @Override
    public Intern saveIntern(Intern intern) {

        validateVacancies(intern.getAreaUniversity().getIdAreaUniversity());

        Role internRole = roleRepository.findById(4L).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setIdRole(4L);
            newRole.setName("practicante");
            return roleRepository.save(newRole);
        });

        intern.getUser().setRole(internRole);

        if (intern.getUser().getIdUser() == null) {
            User savedUser = userService.save(intern.getUser());
            intern.setUser(savedUser);
        }

        return internRepository.save(intern);
    }

    @Override
    public Intern findInternById(Long idIntern) {
        return internRepository.findById(idIntern).orElseThrow(
                () -> new RuntimeException("No se encontró el practicante.")
        );
    }

    @Override
    public List<Intern> getAllInterns() {
        return internRepository.findAll();
    }

    @Override
    public Page<Intern> findAllInterns(Pageable pageable) {
        return internRepository.findAll(pageable);
    }

    @Override
    public Page<Intern> searchInternsByName(String name, Pageable pageable) {
        return internRepository.findByFullName(name, pageable);
    }

    @Override
    public Page<Intern> findInternsByArea(String area, Pageable pageable) {
        return internRepository.findByAreaUniversity_Area_NameContainingIgnoreCase(area, pageable);
    }

    @Override
    public Page<Intern> findInternsByUniversity(String university, Pageable pageable) {
        return internRepository.findByAreaUniversity_University_NameContainingIgnoreCase(university, pageable);
    }

    @Override
    public Page<Intern> findInternsByAreaUniversity(UUID idAreaUniversity, Pageable pageable) {
        return internRepository.findByAreaUniversity_IdAreaUniversity(idAreaUniversity, pageable);
    }

    @Override
    public Long getTotalInterns() {
        return internRepository.count();
    }

    @Override
    public Intern updateIntern(Long idIntern, Intern intern) {

        Intern existingIntern = internRepository.findById(idIntern).orElseThrow(
                () -> new RuntimeException("No se encontró el practicante.")
        );

        if (!existingIntern.getAreaUniversity().getIdAreaUniversity().equals(intern.getAreaUniversity().getIdAreaUniversity())) {
            validateVacancies(intern.getAreaUniversity().getIdAreaUniversity());
        }

        User updatedUser = userService.updateUser(existingIntern.getUser().getIdUser(), intern.getUser());
        existingIntern.setUser(updatedUser);

        existingIntern.setAreaUniversity(intern.getAreaUniversity());
        existingIntern.setTotalHours(intern.getTotalHours());

        return internRepository.save(existingIntern);
    }

    @Override
    @Transactional
    public void deleteIntern(Long idIntern) {

        Intern intern = internRepository.findById(idIntern).orElseThrow(
                () -> new RuntimeException("No se encontró el practicante.")
        );

        assistanceRepository.deleteAllByIntern_IdIntern(idIntern);

        certificatedRepository.deleteAllByIntern_IdIntern(idIntern);

        internRepository.deleteById(idIntern);

        userService.deleteUser(intern.getUser().getIdUser());
    }

}
