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
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

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
        return mapper.toDto(reservationService.getById(reservationId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createReservation(@Valid @RequestBody ReservationDto reservationDto, HttpServletRequest request) {
        Reservation savedReservation = reservationService.save(mapper.toEntity(reservationDto));
        return request.getRequestURL().append(savedReservation.getId()).toString();
    }

    @PutMapping("/{reservationId}")
    public ReservationDto updateReservation(@PathVariable Long reservationId, @Valid @RequestBody ReservationDto reservationDto) {
        if(!reservationId.equals(reservationDto.getId())){
            throw new ValidationException("Reservation ID from URL is not equal to Id from Dto");
        }
        return mapper.toDto(reservationService.update(mapper.toEntity(reservationDto)));
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId) {
        reservationService.delete(reservationId);
    }

}
