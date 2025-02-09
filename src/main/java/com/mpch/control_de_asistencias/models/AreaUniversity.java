package com.mpch.control_de_asistencias.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AreaUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_area_university")
    private UUID idAreaUniversity;

    @ManyToOne
    @JoinColumn(name = "id_area", referencedColumnName = "id_area", nullable = false)
    private Area area;

    @ManyToOne
    @JoinColumn(name = "id_university", referencedColumnName = "id_university", nullable = false)
    private University university;

    @Column(name = "hours_certified", nullable = false)
    private int hoursCertified;

}
