package com.mpch.control_de_asistencias.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Certificated {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCertificated;

    @OneToOne
    @JoinColumn(name = "id_intern", referencedColumnName = "id_intern", nullable = false)
    private Intern intern;

    @Column(nullable = false)
    private boolean status;

    @Column(name = "generated_date", nullable = false)
    private LocalDateTime generatedDate;

}
