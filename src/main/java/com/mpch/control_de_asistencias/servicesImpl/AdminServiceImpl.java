package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.Admin;
import com.mpch.control_de_asistencias.models.Role;
import com.mpch.control_de_asistencias.models.User;
import com.mpch.control_de_asistencias.repositories.AdminRepository;
import com.mpch.control_de_asistencias.repositories.RoleRepository;
import com.mpch.control_de_asistencias.services.AdminService;
import com.mpch.control_de_asistencias.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    /* @Autowired
    private BCryptPasswordEncoder passwordEncoder; */

    // Validar que exista por lo menos un administrador
    private void validateSingleAdmin() {
        if (adminRepository.count() == 1) {
            throw new RuntimeException("El sistema no puede quedar sin administradores.");
        }
    }

    @Override
    public Admin saveAdmin(Admin admin) {

        Role adminRole = roleRepository.findById(1L).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setIdRole(1L);
            newRole.setName("administrador");
            return roleRepository.save(newRole);
        });

        admin.getUser().setRole(adminRole);

        if (admin.getUser().getIdUser() == null) {
            User savedUser = userService.save(admin.getUser());
            admin.setUser(savedUser);
        }

        // admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Long idAdmin) {
        return adminRepository.findById(idAdmin).orElseThrow(
                () -> new RuntimeException("No se encontró el administrador."));
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Page<Admin> getAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public Page<Admin> searchAdminsByName(String name, Pageable pageable) {
        return adminRepository.
                findByFullName(name, pageable);
    }

    @Override
    public Page<Admin> searchAdminsByDni(String dni, Pageable pageable) {
        return adminRepository.
                findByUser_DniContainingIgnoreCase(dni, pageable);
    }

    @Override
    public Long getTotalAdmins() {
        return adminRepository.count();
    }

    @Override
    public Admin updateAdmin(Long idAdmin, Admin admin) {
        Admin existingAdmin = adminRepository.findById(idAdmin).orElseThrow(
                () -> new RuntimeException("No se encontró el administrador.")
        );

        User updatedUser = userService.updateUser(existingAdmin.getUser().getIdUser(), admin.getUser());
        existingAdmin.setUser(updatedUser);

        existingAdmin.setPassword(admin.getPassword());

        return adminRepository.save(existingAdmin);
    }

    @Override
    public void deleteAdmin(Long idAdmin) {
        validateSingleAdmin();

        Optional<Admin> adminOpt = adminRepository.findById(idAdmin);
        if (adminOpt.isEmpty()) {
            throw new RuntimeException("Administrador no encontrado");
        }
        Admin admin = adminOpt.get();
        adminRepository.deleteById(idAdmin);
        userService.deleteUser(admin.getUser().getIdUser());
    }

}
