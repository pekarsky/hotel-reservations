package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.dto.ReservationDto;
import com.example.pekarsky.reservations.mapper.ReservationDtoMapper;
import com.example.pekarsky.reservations.model.Reservation;
import com.example.pekarsky.reservations.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ReservationsResourceTest {

    private static final String URL = "http://";
    private static final long SAME_RESERVATION_ID = 1000L;
    private static final long DIFFERENT_RESERVATION_ID = 9999L;

    @Mock
    private ReservationService reservationServiceMock;

    @Mock
    private ReservationDtoMapper mapper;

    @Mock
    private HttpServletRequest httpServletRequest;

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
    void getReservationByIdShouldCallService() {
        Long reservationId = new Random().nextLong();
        reservationsResource.getReservationById(reservationId);
        verify(reservationServiceMock, only()).getById(ArgumentMatchers.eq(reservationId));
    }

    @Test
    void createReservation() {
        when(reservationServiceMock.save(any())).thenReturn(new Reservation());
        when(mapper.toEntity(any())).thenReturn(new Reservation());
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(URL));
        reservationsResource.createReservation(new ReservationDto(), httpServletRequest);
        verify(reservationServiceMock, only()).save(any(Reservation.class));
    }

    @Test
    void updateReservationShouldCallServiceOnSameReservationIdInUrlAndDto() {
        reservationsResource.updateReservation(SAME_RESERVATION_ID, ReservationDto.builder().id(SAME_RESERVATION_ID).build());
        verify(reservationServiceMock, only()).update(any());
    }

    @Test
    void updateReservationShouldThrowExceptionOnDifferentReservationIdInUrlAndDto() {
        assertThrows(ValidationException.class,
                () -> reservationsResource.updateReservation(SAME_RESERVATION_ID, ReservationDto.builder().id(DIFFERENT_RESERVATION_ID).build()));
    }

    @Test
    void deleteReservationShouldCallService() {
        Long reservationId = new Random().nextLong();
        reservationsResource.deleteReservation(reservationId);
        verify(reservationServiceMock, only()).delete(reservationId);
    }
}