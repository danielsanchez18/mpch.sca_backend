package com.mpch.control_de_asistencias.servicesImpl;

import com.mpch.control_de_asistencias.models.User;
import com.mpch.control_de_asistencias.repositories.UserRepository;
import com.mpch.control_de_asistencias.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Validar unicidad de DNI
    private void validateUniqueDni(String dni) {
        if (userRepository.existsByDni(dni)) {
            throw new RuntimeException("Ya existe un usuario con el DNI '" + dni + "'");
        }
    }

    // Validar que el usuario sea mayor de edad
    private void validateAge(Date birthdate) {
        LocalDate today = LocalDate.now();
        LocalDate birth = birthdate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        if (Period.between(birth, today).getYears() < 18) {
            throw new RuntimeException("El usuario debe ser mayor de 18 años para registrarse.");
        }
    }

    @Override
    public User save(User user) {
        validateUniqueDni(user.getDni());
        validateAge(user.getBirthdate());
        user.setCreatedAt(LocalDate.now().atStartOfDay());
        user.setUpdatedAt(LocalDate.now().atStartOfDay());
        return userRepository.save(user);
    }

    @Override
    public User findUserById(UUID idUser) {
        return userRepository.findById(idUser).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchUsersByName(String name, Pageable pageable) {
        return userRepository.findByFullNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<User> findUsersByRole(Long idRole, Pageable pageable) {
        return userRepository.findByRole_IdRole(idRole, pageable);
    }

    @Override
    public Page<User> searchUsersByDni(String dni, Pageable pageable) {
        return userRepository.findByDniContainingIgnoreCase(dni, pageable);
    }

    @Override
    public Page<User> findUsersByStatus(boolean status, Pageable pageable) {
        return userRepository.findByStatus(status, pageable);
    }

    @Override
    public Long getTotalUsers() {
        return userRepository.count();
    }

    @Override
    public User updateUser(UUID idUser, User user) {
        User existingUser = userRepository.findById(idUser).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );

        if (!existingUser.getDni().equals(user.getDni())) {
            validateUniqueDni(user.getDni());
        }
        validateAge(user.getBirthdate());

        existingUser.setName(user.getName());
        existingUser.setLastname(user.getLastname());
        existingUser.setDni(user.getDni());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setPhoto(user.getPhoto());
        existingUser.setStatus(user.isStatus());
        existingUser.setUpdatedAt(LocalDate.now().atStartOfDay());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID idUser) {
        if (!userRepository.existsById(idUser)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(idUser);
    }
}
