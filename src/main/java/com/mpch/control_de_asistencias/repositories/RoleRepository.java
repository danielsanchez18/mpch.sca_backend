package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
