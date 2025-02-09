package com.mpch.control_de_asistencias.services;

import com.mpch.control_de_asistencias.models.Assistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AssistanceService {

    Assistance registerCheckIn(String dni);

    Assistance registerCheckOut(String dni);

    Page<Assistance> getAssistancesByDate(LocalDate date, Pageable pageable);

    Page<Assistance> getAssistancesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Assistance> searchAssistancesByInternName(String name, Pageable pageable);

    Page<Assistance> findAssistancesByArea(String areaName, Pageable pageable);

    double getHoursWorkedByInternOnDate(String dni, LocalDate date);

    double getMonthlyHoursWorkedByArea(String areaName, int month, int year);

    double getMonthlyHoursWorkedByUniversity(String universityName, int month, int year);

    boolean hasOverlap(String idIntern, LocalDateTime checkIn, LocalDateTime checkOut);

}
