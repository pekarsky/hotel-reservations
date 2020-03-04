package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.dto.ReservationDto;
import com.example.pekarsky.reservations.exception.ReservationNotFoundException;
import com.example.pekarsky.reservations.mapper.ReservationDtoMapper;
import com.example.pekarsky.reservations.model.Reservation;
import com.example.pekarsky.reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationsResource {

    private final ReservationService reservationService;
    private final ReservationDtoMapper mapper;

    @GetMapping
    public List<ReservationDto> getReservations(@RequestParam(value = "roomNumber", required = false) Integer roomNumber) {
        if (roomNumber == null) {
            return mapper.toDtoList(reservationService.getAllReservations());
        } else {
            return mapper.toDtoList(reservationService.getReservationsByRoomNumber(roomNumber));
        }
    }

    @GetMapping("/{reservationId}")
    public ReservationDto getReservationById(@PathVariable Long reservationId) throws ReservationNotFoundException {
        return mapper.toDto(Optional.ofNullable(reservationService.getById(reservationId)).orElseThrow(ReservationNotFoundException::new));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createReservation(@RequestBody ReservationDto reservationDto, HttpServletRequest request) {
        Reservation savedReservation = reservationService.save(mapper.toEntity(reservationDto));
        return request.getRequestURL().append(savedReservation.getId()).toString();
    }

    @PutMapping("/{reservationId}")
    public ReservationDto updateReservation(@PathVariable Integer reservationId, @RequestBody ReservationDto reservation) {
        return null;
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable String reservationId) {

    }

}
