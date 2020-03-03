package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class ReservationsResourceTest {

    @Mock
    private ReservationService reservationServiceMock;

    @InjectMocks
    private ReservationsResource reservationsResource;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetAllReservationsOnEmptyId() {
        reservationsResource.getReservations(null);
        verify(reservationServiceMock, only()).getAllReservations();
    }

    @Test
    void testGetAllReservationsOnNotEmptyId() {
        reservationsResource.getReservations(10);
        verify(reservationServiceMock, only()).getReservationsByRoomNumber(ArgumentMatchers.eq(10));
    }

    @Test
    void getReservationById() {
    }

    @Test
    void createReservation() {
    }

    @Test
    void updateReservation() {
    }

    @Test
    void deleteReservation() {
    }
}