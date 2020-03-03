package com.example.pekarsky.reservations.service;

import com.example.pekarsky.reservations.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsByRoomNumber(Integer roomNumber);

}
