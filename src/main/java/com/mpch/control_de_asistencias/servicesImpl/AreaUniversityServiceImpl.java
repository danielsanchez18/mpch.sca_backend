package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.AreaUniversity;
import com.mpch.control_de_asistencias.repositories.AreaUniversityRepository;
import com.mpch.control_de_asistencias.repositories.InternRepository;
import com.mpch.control_de_asistencias.services.AreaUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AreaUniversityServiceImpl implements AreaUniversityService {

    @Autowired
    private AreaUniversityRepository areaUniversityRepository;

    @Autowired
    private InternRepository internRepository;

    // Validar que la combinación de área y universidad sea única
    private void validateUniqueCombination(Long areaId, Long universityId) {
        if (areaUniversityRepository.existsByArea_IdAreaAndUniversity_IdUniversity(areaId, universityId)) {
            throw new RuntimeException("La universidad ya está asociada a esta área");
        }
    }

    private void validateAreaUniversityInUse(UUID idAreaUniversity) {
        boolean isInUse = internRepository.existsByAreaUniversity_IdAreaUniversity(idAreaUniversity);
        if (isInUse) {
            throw new RuntimeException("No se puede eliminar la asociación porque tiene practicantes asignados.");
        }
    }

    @Override
    public AreaUniversity addAreaUniversity(AreaUniversity areaUniversity) {
        validateUniqueCombination(
                areaUniversity.getArea().getIdArea(),
                areaUniversity.getUniversity().getIdUniversity()
        );
        return areaUniversityRepository.save(areaUniversity);
    }

    @Override
    public AreaUniversity getAreaUniversityById(UUID idAreaUniversity) {
        return areaUniversityRepository.findById(idAreaUniversity)
                .orElseThrow(() -> new RuntimeException("Relación Área-Universidad no encontrada"));
    }

    @Override
    public List<AreaUniversity> getAllAreaUniversities() {
        return areaUniversityRepository.findAll();
    }

    @Override
    public Page<AreaUniversity> getAllAreaUniversities(Pageable pageable) {
        return areaUniversityRepository.findAll(pageable);
    }

    @Override
    public Page<AreaUniversity> searchByAreaOrUniversity(String areaName, String nameUniversity, Pageable pageable) {
        return areaUniversityRepository.findByArea_NameContainingIgnoreCaseOrUniversity_NameContainingIgnoreCase(
                areaName, nameUniversity, pageable
        );
    }

    @Override
    public void deleteAreaUniversity(UUID idAreaUniversity) {
        validateAreaUniversityInUse(idAreaUniversity);
        areaUniversityRepository.deleteById(idAreaUniversity);
    }

    @Override
    public Page<AreaUniversity> getAreasByUniversity(Long idUniversity, Pageable pageable) {
        return areaUniversityRepository.findByUniversity_IdUniversity(idUniversity, pageable);
    }

    @Override
    public Page<AreaUniversity> getUniversitiesByArea(Long idArea, Pageable pageable) {
        return areaUniversityRepository.findByArea_IdArea(idArea, pageable);
    }

}
