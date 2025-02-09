package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    Admin saveAdmin(Admin admin);

    Admin getAdminById(Long idAdmin);

    List<Admin> getAllAdmins();

    Page<Admin> getAllAdmins(Pageable pageable);

    Page<Admin> searchAdminsByName(String name, Pageable pageable);

    Page<Admin> searchAdminsByDni(String dni, Pageable pageable);

    Long getTotalAdmins();

    Admin updateAdmin(Long idAdmin, Admin admin);

    void deleteAdmin(Long idAmind);

}
