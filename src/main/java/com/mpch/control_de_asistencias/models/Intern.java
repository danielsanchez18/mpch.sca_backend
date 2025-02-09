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
public class Intern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_intern", nullable = false)
    private Long idIntern;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_area_university", referencedColumnName = "id_area_university", nullable = false)
    private AreaUniversity areaUniversity;

    @Column(name = "total_hours", nullable = false)
    private double totalHours;

}
