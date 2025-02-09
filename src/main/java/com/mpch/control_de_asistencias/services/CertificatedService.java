package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.Certificated;
import com.mpch.control_de_asistencias.models.Intern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CertificatedService {

    Certificated generateCertificated(String dni);

    Certificated findById(UUID id);

    Certificated findCertificateByIntern(String dni);

    Page<Certificated> findAllCertificates(Pageable pageable);

    Page<Certificated> searchCertificatesByInternName(String name, Pageable pageable);

    Page<Certificated> findCertifiedInterns(Pageable pageable);

    Page<Intern> findEligibleInternsForCertification(Pageable pageable);

}
