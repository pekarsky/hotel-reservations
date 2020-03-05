package com.example.pekarsky.reservations.service;

import com.example.pekarsky.reservations.exception.ReservationNotFoundException;
import com.example.pekarsky.reservations.jpa.ReservationRepository;
import com.example.pekarsky.reservations.model.Reservation;
import com.example.pekarsky.reservations.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ReservationServiceImplTest {

    public static final long EXISTING_RESERVATION_ID = 1000L;
    public static final long NONEXISTENT_RESERVATION_ID = 9999L;
    public static final int ROOM_NUMBER = 101;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void init() {
        initMocks(this);
    }

    @Test
    void getByIdShouldReturnIfExist() {
        when(reservationRepository.findById(EXISTING_RESERVATION_ID)).thenReturn(Optional.of(new Reservation()));
        Reservation reservation = reservationService.getById(EXISTING_RESERVATION_ID);
        assertNotNull(reservation);
        verify(reservationRepository).findById(EXISTING_RESERVATION_ID);
    }

    @Test
    void getByIdShouldFailOnNonExistentReservationId() {
        assertThrows(ReservationNotFoundException.class, () -> reservationService.getById(NONEXISTENT_RESERVATION_ID));
    }

    @Test
    void getAllReservationsShouldCallRepository(){
        when(reservationRepository.findAll()).thenReturn(Collections.emptyList());
        List<Reservation> reservations = reservationService.getAllReservations();
        assertNotNull(reservations);
        verify(reservationRepository).findAll();
    }

    @Test
    void getReservationsByRoomNumber() {
        when(reservationRepository.findByRoomNumber(ROOM_NUMBER)).thenReturn(Collections.emptyList());
        List<Reservation> reservations = reservationService.getReservationsByRoomNumber(ROOM_NUMBER);
        assertNotNull(reservations);
        verify(reservationRepository).findByRoomNumber(ROOM_NUMBER);
    }

    @Test
    void saveShouldCallRepositoryAndSetId() {
        when(reservationRepository.save(any(Reservation.class))).thenAnswer((Answer<Reservation>) invocation -> {
            Reservation r = invocation.getArgument(0);
            r.setId(new Random().nextLong());
            return r;
        });
        Reservation savedReservation = reservationService.save(new Reservation());
        assertNotNull(savedReservation);
        verify(reservationRepository).save(savedReservation);
        assertNotNull(savedReservation.getId());
    }

    @Test
    void updateShouldFailOnNonExistentReservation() {
        Reservation instance = new Reservation();
        instance.setId(new Random().nextLong());
        assertThrows(ReservationNotFoundException.class, () -> reservationService.update(instance));
    }

    @Test
    void updateShouldUpdateFieldsOnExistentReservation() {

        Reservation savedReservation = new Reservation();
        savedReservation.setId(EXISTING_RESERVATION_ID);
        savedReservation.setGuests(4);

        LocalDate newStartDate = LocalDate.of(2020, 3, 10);
        LocalDate newEndDate = LocalDate.of(2020, 3, 20);
        int newGuestsCount = 5;

        Reservation newReservation = new Reservation();
        newReservation.setId(EXISTING_RESERVATION_ID);
        newReservation.setRoom(new Room());
        newReservation.setGuests(newGuestsCount);
        newReservation.setStartDate(newStartDate);
        newReservation.setEndDate(newEndDate);

        when(reservationRepository.findById(EXISTING_RESERVATION_ID)).thenReturn(Optional.of(savedReservation));
        when(reservationRepository.save(savedReservation)).thenAnswer((Answer<Reservation>) invocation -> invocation.getArgument(0));

        Reservation updatedReservation = reservationService.update(newReservation);

        assertEquals(updatedReservation.getId(), newReservation.getId());
        assertEquals(updatedReservation.getRoom(), newReservation.getRoom());
        assertEquals(updatedReservation.getGuests(), newReservation.getGuests());
        assertEquals(updatedReservation.getStartDate(), newReservation.getStartDate());
        assertEquals(updatedReservation.getEndDate(), newReservation.getEndDate());

        verify(reservationRepository).save(savedReservation);
    }

    @Test
    void deleteShouldSucceedOnExistingReservationId() {
        when(reservationRepository.existsById(EXISTING_RESERVATION_ID)).thenReturn(true);

        reservationService.delete(EXISTING_RESERVATION_ID);

        verify(reservationRepository).existsById(EXISTING_RESERVATION_ID);
        verify(reservationRepository).deleteById(EXISTING_RESERVATION_ID);
    }

    @Test
    void deleteShouldSucceedOnNonExistingReservationId() {
        when(reservationRepository.existsById(NONEXISTENT_RESERVATION_ID)).thenReturn(false);

        reservationService.delete(NONEXISTENT_RESERVATION_ID);

        verify(reservationRepository).existsById(NONEXISTENT_RESERVATION_ID);
        verify(reservationRepository, never()).deleteById(NONEXISTENT_RESERVATION_ID);
    }

}