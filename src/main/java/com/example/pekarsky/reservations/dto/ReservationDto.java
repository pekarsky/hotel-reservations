package com.example.pekarsky.reservations.dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private Long id;
    private Integer roomNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;
}
