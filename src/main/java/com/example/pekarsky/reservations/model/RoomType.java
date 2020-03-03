package com.example.pekarsky.reservations.model;

public enum RoomType {
    BASIC(4),
    SUITE(6),
    PENTHOUSE(8);

    public int capacity;

    RoomType(int capacity){
        this.capacity = capacity;
    }
}
