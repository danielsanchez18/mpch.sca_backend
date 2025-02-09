package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.Security;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SecurityService {

    Security saveSecurity(Security security);

    Security getSecurityById(Long idSecurity);

    List<Security> getAllSecurities();

    Page<Security> getAllSecurities(Pageable pageable);

    Page<Security> searchSecuritiesByName(String name, Pageable pageable);

    Page<Security> searchSecuritiesByDni(String dni, Pageable pageable);

    Long getTotalSecurities();

    Security updateSecurity(Long idSecurity, Security security);

    void deleteSecurity(Long idSecurity);

}
