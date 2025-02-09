package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.University;
import com.mpch.control_de_asistencias.repositories.InternRepository;
import com.mpch.control_de_asistencias.repositories.UniversityRepository;
import com.mpch.control_de_asistencias.services.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private InternRepository internRepository;

    // Validar nombre único
    private void validateUniqueName(String name) {
        if (universityRepository.existsByName(name)) {
            throw new RuntimeException("La universidad con el nombre '" + name + "' ya existe.");
        }
    }

    // Validar universidad existente
    private void validateExisting(Long idUniversity) {
        if (!universityRepository.existsById(idUniversity)) {
            throw new IllegalArgumentException("Universidad no encontrada con el ID " + idUniversity);
        }
    }

    // Validar que la universidad no tenga estudiantes registrados
    private void validateUniversityInUse(Long idUniversity) {
        boolean isInUse = internRepository.existsByAreaUniversity_University_IdUniversity(idUniversity);
        if (isInUse) {
            throw new RuntimeException("La universidad no se puede eliminar porque tiene estudiantes registrados.");
        }
    }

    @Override
    public University createUniversity(University university) {
        validateUniqueName(university.getName());
        return universityRepository.save(university);
    }

    @Override
    public University getUniversityById(Long idUniversity) {
        return universityRepository.findById(idUniversity).orElseThrow(
                () -> new RuntimeException("Universidad no encontrada")
        );
    }

    @Override
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

    @Override
    public Page<University> getAllUniversities(Pageable pageable) {
        return universityRepository.findAll(pageable);
    }

    @Override
    public Page<University> searchUniversityByName(String name, Pageable pageable) {
        return universityRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Long getTotalUniversities() {
        return universityRepository.count();
    }

    @Override
    public University updateUniversity(Long idUniversity, University university) {
        validateExisting(idUniversity);

        // Obtener la universidad actual para comparar su nombre
        University existingUniversity = universityRepository.findById(idUniversity).orElseThrow();

        // Validar que el nombre no esté siendo usado por otra universidad, pero no por la misma universidad
        if (!existingUniversity.getName().equals(university.getName()) && universityRepository.existsByName(university.getName())) {
            throw new RuntimeException("La universidad con el nombre '" + university.getName() + "' ya existe.");
        }

        // Actualizar la universidad
        existingUniversity.setName(university.getName());
        existingUniversity.setAcronym(university.getAcronym());
        existingUniversity.setPhoto(university.getPhoto());
        existingUniversity.setStatus(university.isStatus());

        return universityRepository.save(existingUniversity);
    }

    @Override
    public void deleteUniversity(Long idUniversity) {
        validateExisting(idUniversity);
        validateUniversityInUse(idUniversity);

        universityRepository.deleteById(idUniversity);
    }
}
