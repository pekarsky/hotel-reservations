package com.example.pekarsky.reservations.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Reservation {
    private Room room;
    private Date startDate;
    private Date endDate;
    private int guests;
}
