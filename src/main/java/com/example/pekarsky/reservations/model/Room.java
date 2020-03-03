package com.example.pekarsky.reservations.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Room {
    @Id
    private Integer number;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
}
