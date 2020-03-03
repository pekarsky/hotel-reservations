package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.model.Reservation;
import com.example.pekarsky.reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Reservation getReservationById(@PathVariable Integer reservationId){
        return null;
    }

    @PostMapping
    public URL createReservation(Reservation reservation){
        return null;
    }

    @PutMapping("/{reservationId}")
    public Reservation updateReservation(@PathVariable Integer reservationId, Reservation reservation){
        return null;
    }

    @DeleteMapping("/{reservationId}")
    public void deleteReservation(@PathVariable String reservationId){

    }
//
//    @GetMapping
//    public List<Reservation> getReservationByRoomNumber(){
//        return null;
//    }
}
