package com.example.pekarsky.reservations.validation;

import com.example.pekarsky.reservations.dto.ReservationDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EndDateAfterStartDateValidatorTest {

    @Test
    void isValidSuccess() {
        ReservationDto dto = ReservationDto.builder()
                    .roomNumber(101)
                    .startDate(LocalDate.of(2020, 3, 1))
                    .endDate(LocalDate.of(2020, 3, 9))
                    .build();
        assertTrue(new EndDateAfterStartDateValidator().isValid(dto, null));
    }

    @Test
    void isValidFail(){
        ReservationDto dto = ReservationDto.builder()
                .roomNumber(101)
                .endDate(LocalDate.of(2020, 3, 1))
                .startDate(LocalDate.of(2020, 3, 9))
                .build();
        assertFalse(new EndDateAfterStartDateValidator().isValid(dto, null));
    }
}