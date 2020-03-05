package com.example.pekarsky.reservations.mapper;

import com.example.pekarsky.reservations.dto.ReservationDto;
import com.example.pekarsky.reservations.model.Reservation;
import com.example.pekarsky.reservations.model.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ReservationDtoMapperTest {

    private ReservationDtoMapperImpl mapper = new ReservationDtoMapperImpl();

    @Test
    void toDto() {
        Reservation entity = new Reservation();
        entity.setId(new Random().nextLong());
        Room room = new Room();
        room.setNumber(new Random().nextInt());
        entity.setRoom(room);
        entity.setStartDate(LocalDate.now());
        entity.setStartDate(LocalDate.now());
        entity.setGuests(new Random().nextInt());
        ReservationDto dto = mapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getRoom().getNumber(), dto.getRoomNumber());
        assertEquals(entity.getStartDate(), dto.getStartDate());
        assertEquals(entity.getEndDate(), dto.getEndDate());
        assertEquals(entity.getGuests(), dto.getGuests());
    }

    @Test
    void toEntity() {
        ReservationDto dto = ReservationDto.builder()
                .id(new Random().nextLong())
                .roomNumber(101)
                .startDate(LocalDate.of(2020, 3, 1))
                .endDate(LocalDate.of(2020, 3, 9))
                .guests(new Random().nextInt())
                .build();
        Reservation entity = mapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getRoomNumber(), entity.getRoom().getNumber());
        assertEquals(dto.getStartDate(), entity.getStartDate());
        assertEquals(dto.getEndDate(), entity.getEndDate());
        assertEquals(dto.getGuests(), entity.getGuests());
    }

    @Test
    void toDtoListShouldProperlyMapList() {
        Reservation entity = new Reservation();
        entity.setId(new Random().nextLong());
        Room room = new Room();
        room.setNumber(new Random().nextInt());
        entity.setRoom(room);
        entity.setStartDate(LocalDate.now());
        entity.setStartDate(LocalDate.now());
        entity.setGuests(new Random().nextInt());
        List<ReservationDto> dtoList = mapper.toDtoList(Arrays.asList(entity, new Reservation()));
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        ReservationDto dto = dtoList.get(0);
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getRoom().getNumber(), dto.getRoomNumber());
        assertEquals(entity.getStartDate(), dto.getStartDate());
        assertEquals(entity.getEndDate(), dto.getEndDate());
        assertEquals(entity.getGuests(), dto.getGuests());
    }

    @Test
    void toDtoListShouldReturnNullOnNullArgument() {
        List<ReservationDto> dto = mapper.toDtoList(null);
        assertNull(dto);
    }
}