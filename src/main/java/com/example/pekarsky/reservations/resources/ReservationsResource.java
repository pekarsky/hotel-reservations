package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.model.Reservation;
import com.example.pekarsky.reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationsResource {

    private final ReservationService reservationService;

    @GetMapping
    public List<Reservation> Reservations(@RequestParam(value = "roomNumber", required = false) Integer roomNumber){
        if(roomNumber == null){
            return reservationService.getAllReservations();
        } else {
            return reservationService.getReservationsByRoomNumber(roomNumber);
        }
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable Long reservationId){
        return reservationService.getById(reservationId);
    }

    @PostMapping
    public URL createReservation(@RequestBody Reservation reservation, HttpServletRequest request) throws MalformedURLException {
        Reservation savedReservation = reservationService.save(reservation);
        return new URL(request.getRequestURL().append(savedReservation.getId()).toString());
    }

    @PutMapping("/{reservationId}")
    public Reservation updateReservation(@PathVariable Integer reservationId, @RequestBody Reservation reservation){
        return null;
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable String reservationId){

    }

}
