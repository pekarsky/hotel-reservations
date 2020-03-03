package com.example.pekarsky.reservations.jpa;

import com.example.pekarsky.reservations.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomNumber(Integer roomNumber);
}
