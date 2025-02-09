package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.Certificated;
import com.mpch.control_de_asistencias.models.Intern;
import com.mpch.control_de_asistencias.repositories.CertificatedRepository;
import com.mpch.control_de_asistencias.repositories.InternRepository;
import com.mpch.control_de_asistencias.services.CertificatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CertificatedServiceImpl implements CertificatedService {

    @Autowired
    private CertificatedRepository certificatedRepository;

    @Autowired
    private InternRepository internRepository;

    @Override
    public Certificated generateCertificated(String dni) {

        // Validar que el practicante exista
        Intern intern = internRepository.findByUser_Dni(dni).orElseThrow(
                () -> new RuntimeException("No se encontró el practicante con DNI: " + dni)
        );

        // Validar que cumpla las horas requeridas
        if (intern.getTotalHours() < intern.getAreaUniversity().getHoursCertified()) {
            throw new RuntimeException("El practicante no cumple con las horas requeridas para la certificación.");
        }

        // Validar que no exista un certificado previo
        if (certificatedRepository.findByIntern_User_Dni(dni).isPresent()) {
            throw new RuntimeException("El practicante ya tiene un certificado generado.");
        }

        // Generar el certificado
        Certificated certificated = new Certificated();
        certificated.setIntern(intern);
        certificated.setStatus(true);
        certificated.setGeneratedDate(LocalDateTime.now());

        // TODO: Generar y guardar el PDF aquí (ver siguiente sección)

        return certificatedRepository.save(certificated);
    }

    @Override
    public Certificated findById(UUID id) {
        return certificatedRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontró un certificado con ID: " + id)
        );
    }

    @Override
    public Certificated findCertificateByIntern(String dni) {
        return certificatedRepository.findByIntern_User_Dni(dni).orElseThrow(
                () -> new RuntimeException("No se encontró un certificado para el practicante con DNI: " + dni)
        );
    }

    @Override
    public Page<Certificated> findAllCertificates(Pageable pageable) {
        return certificatedRepository.findAll(pageable);
    }

    @Override
    public Page<Certificated> searchCertificatesByInternName(String name, Pageable pageable) {
        return certificatedRepository.findByInternFullName(name, pageable);
    }

    @Override
    public Page<Certificated> findCertifiedInterns(Pageable pageable) {
        return certificatedRepository.findByStatus(true, pageable);
    }

    @Override
    public Page<Intern> findEligibleInternsForCertification(Pageable pageable) {
        return internRepository.findEligibleInterns(pageable); // Implementar en el repositorio
    }
}
