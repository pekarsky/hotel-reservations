package com.example.pekarsky.reservations.jpa;

import com.example.pekarsky.reservations.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
