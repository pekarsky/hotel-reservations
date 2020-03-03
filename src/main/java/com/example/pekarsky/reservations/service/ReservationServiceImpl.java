package com.example.pekarsky.reservations.service;

import com.example.pekarsky.reservations.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public List<Reservation> getAllReservations() {
        return null;
    }

    @Override
    public List<Reservation> getReservationsByRoomNumber(Integer roomNumber) {
        return null;
    }
}
