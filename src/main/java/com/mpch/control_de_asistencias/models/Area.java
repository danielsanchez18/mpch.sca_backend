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
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Long idArea;

    @Column(unique = true, nullable = false, length = 25)
    private String name;

    @Column(name = "nro_vacancies", nullable = false)
    private Long nroVacancies;

    @Column(nullable = false)
    private boolean status;

}
