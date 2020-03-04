package com.example.pekarsky.reservations.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EndDateAfterStartDateValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateAfterStartDate {
    String message() default "Reservation End Date should be after Start Date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
