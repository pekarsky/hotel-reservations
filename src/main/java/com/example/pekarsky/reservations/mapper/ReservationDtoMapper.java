package com.example.pekarsky.reservations.mapper;

import com.example.pekarsky.reservations.dto.ReservationDto;
import com.example.pekarsky.reservations.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {
    @Mapping(source = "room.number", target = "roomNumber")
    ReservationDto toDto(Reservation reservation);

    @Mapping(source = "roomNumber", target = "room.number")
    Reservation toEntity(ReservationDto dto);

    List<ReservationDto> toDtoList(List<Reservation> entityList);
}