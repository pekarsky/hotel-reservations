package com.example.pekarsky.reservations.validation;

import com.example.pekarsky.reservations.dto.ReservationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, ReservationDto> {
    @Override
    public boolean isValid(ReservationDto reservationDto, ConstraintValidatorContext constraintValidatorContext) {
        //this validation applies to entire Dto, so will be applied before fields validation. These fields are already marked as @NotNull
        if(reservationDto.getStartDate() == null || reservationDto.getEndDate() == null){
            return true;
        }
        return reservationDto.getEndDate().isAfter(reservationDto.getStartDate());
    }
}
