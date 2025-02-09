package com.mpch.control_de_asistencias.repositories;

import com.mpch.control_de_asistencias.models.Assistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface AssistanceRepository extends JpaRepository<Assistance, UUID> {

    @Query("SELECT a FROM Assistance a WHERE a.intern.idIntern = :idIntern AND a.checkOut IS NULL")
    Optional<Assistance> findActiveAssistanceByIntern(@Param("idIntern") String idIntern);

    @Query("SELECT COUNT(a) > 0 FROM Assistance a WHERE a.intern.idIntern = :idIntern " +
            "AND ((:checkIn BETWEEN a.checkIn AND a.checkOut) OR (:checkOut BETWEEN a.checkIn AND a.checkOut))")
    boolean hasOverlap(@Param("idIntern") String idIntern, @Param("checkIn") LocalDateTime checkIn, @Param("checkOut") LocalDateTime checkOut);

    Page<Assistance> findByIntern_AreaUniversity_Area_Name(String nameArea, Pageable pageable);

    @Query("SELECT a FROM Assistance a " +
            "WHERE LOWER(CONCAT(a.intern.user.name, ' ', a.intern.user.lastname)) " +
            "LIKE LOWER(CONCAT('%', :fullName, '%'))")
    Page<Assistance> findByInternFullName(@Param("fullName") String fullName, Pageable pageable);

    @Query("SELECT SUM(a.hoursWorked) FROM Assistance a WHERE a.intern.idIntern = :idIntern " +
            "AND a.checkIn BETWEEN :start AND :end")
    double sumHoursWorkedByInternAndDate(@Param("idIntern") String idIntern,
                                         @Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    @Query("SELECT SUM(a.hoursWorked) FROM Assistance a WHERE a.intern.areaUniversity.area.name = :areaName " +
            "AND FUNCTION('MONTH', a.checkIn) = :month AND FUNCTION('YEAR', a.checkIn) = :year")
    double sumHoursWorkedByAreaAndMonth(@Param("areaName") String areaName,
                                        @Param("month") int month,
                                        @Param("year") int year);

    @Query("SELECT SUM(a.hoursWorked) FROM Assistance a WHERE a.intern.areaUniversity.university.name = :universityName " +
            "AND FUNCTION('MONTH', a.checkIn) = :month AND FUNCTION('YEAR', a.checkIn) = :year")
    double sumHoursWorkedByUniversityAndMonth(@Param("universityName") String universityName,
                                              @Param("month") int month,
                                              @Param("year") int year);

    @Query("SELECT a FROM Assistance a WHERE a.checkIn BETWEEN :start AND :end")
    Page<Assistance> findAllByCheckInBetween(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end,
                                             Pageable pageable);

    @Query("SELECT COALESCE(SUM(a.hoursWorked), 0) FROM Assistance a WHERE a.intern.idIntern = :idIntern")
    double getTotalHoursWorkedByIntern(@Param("idIntern") Long idIntern);

    void deleteAllByIntern_IdIntern(Long idIntern);
}