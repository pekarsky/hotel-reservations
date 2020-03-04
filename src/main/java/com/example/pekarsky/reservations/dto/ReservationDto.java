package com.example.pekarsky.reservations.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ReservationDto {
    private Long id;
    private Integer roomNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;
}
