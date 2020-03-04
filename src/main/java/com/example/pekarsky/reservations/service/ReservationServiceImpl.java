package com.example.pekarsky.reservations.service;

import com.example.pekarsky.reservations.exception.ReservationNotFoundException;
import com.example.pekarsky.reservations.jpa.ReservationRepository;
import com.example.pekarsky.reservations.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public Reservation getById(Long id) {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByRoomNumber(Integer roomNumber) {
        return reservationRepository.findByRoomNumber(roomNumber);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        Reservation persistedReservation = reservationRepository.findById(reservation.getId()).orElseThrow(ReservationNotFoundException::new);
        //TODO: manual fields propagation probably is not the best option
        persistedReservation.setRoom(reservation.getRoom());
        persistedReservation.setStartDate(reservation.getStartDate());
        persistedReservation.setEndDate(reservation.getEndDate());
        persistedReservation.setGuests(reservation.getGuests());
        return reservationRepository.save(persistedReservation);
    }

    @Override
    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
