package com.mpch.control_de_asistencias.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_university")
    private Long idUniversity;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10)
    private String acronym;

    private String photo;

    @Column(nullable = false)
    private boolean status;

}
