package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.Role;
import com.mpch.control_de_asistencias.models.Security;
import com.mpch.control_de_asistencias.models.User;
import com.mpch.control_de_asistencias.repositories.RoleRepository;
import com.mpch.control_de_asistencias.repositories.SecurityRepository;
import com.mpch.control_de_asistencias.services.SecurityService;
import com.mpch.control_de_asistencias.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    /* @Autowired
    private BCryptPasswordEncoder passwordEncoder; */

    @Override
    public Security saveSecurity(Security security) {

        Role securityRole = roleRepository.findById(3L).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setIdRole(3L);
            newRole.setName("seguridad");
            return roleRepository.save(newRole);
        });

        security.getUser().setRole(securityRole);

        if (security.getUser().getIdUser() == null) {
            User savedUser = userService.save(security.getUser());
            security.setUser(savedUser);
        }

        // security.setPassword(passwordEncoder.encode(security.getPassword()));
        return securityRepository.save(security);
    }

    @Override
    public Security getSecurityById(Long idSecurity) {
        return securityRepository.findById(idSecurity).orElseThrow(
                () -> new RuntimeException("No se encontró al seguridad."));
    }

    @Override
    public List<Security> getAllSecurities() {
        return securityRepository.findAll();
    }

    @Override
    public Page<Security> getAllSecurities(Pageable pageable) {
        return securityRepository.findAll(pageable);
    }

    @Override
    public Page<Security> searchSecuritiesByName(String name, Pageable pageable) {
        return securityRepository.findByFullName(name, pageable);
    }

    @Override
    public Page<Security> searchSecuritiesByDni(String dni, Pageable pageable) {
        return securityRepository.
                findByUser_DniContainingIgnoreCase(dni, pageable);
    }

    @Override
    public Long getTotalSecurities() {
        return securityRepository.count();
    }

    @Override
    public Security updateSecurity(Long idSecurity, Security security) {
        Security existingSecurity = securityRepository.findById(idSecurity).orElseThrow(
                () -> new RuntimeException("No se encontró el seguridad.")
        );

        User updatedUser = userService.updateUser(existingSecurity.getUser().getIdUser(), security.getUser());
        existingSecurity.setUser(updatedUser);

        existingSecurity.setPassword(security.getPassword());

        return securityRepository.save(existingSecurity);
    }

    @Override
    public void deleteSecurity(Long idSecurity) {
        Optional<Security> securityOpt = securityRepository.findById(idSecurity);
        if (securityOpt.isEmpty()) {
            throw new RuntimeException("Seguridad no encontrado");
        }
        Security security = securityOpt.get();
        securityRepository.deleteById(idSecurity);
        userService.deleteUser(security.getUser().getIdUser());
    }

}
