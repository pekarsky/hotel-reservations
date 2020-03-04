package com.example.pekarsky.reservations.service;

import com.example.pekarsky.reservations.model.Reservation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ReservationService {
    Reservation getById(Long id);
    List<Reservation> getAllReservations();
    List<Reservation> getReservationsByRoomNumber(Integer roomNumber);
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    void delete(Long reservationId);
}
