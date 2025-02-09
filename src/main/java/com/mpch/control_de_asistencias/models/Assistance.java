package com.mpch.control_de_asistencias.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Assistance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_assistance", nullable = false)
    private UUID idAssistance;

    @ManyToOne
    @JoinColumn(name = "id_intern", referencedColumnName = "id_intern", nullable = false)
    private Intern intern;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "hours_worked", nullable = false)
    private double hoursWorked;

    @PrePersist
    @PreUpdate
    private void calculateHoursWorked() {
        if (checkIn != null && checkOut != null) {
            long seconds = Duration.between(checkIn, checkOut).getSeconds();
            this.hoursWorked = Math.min(10.0, seconds / 3600.0); // Máximo 10 horas
        } else {
            this.hoursWorked = 0.0; // Si no hay Check-Out, no hay horas
        }
    }

}
