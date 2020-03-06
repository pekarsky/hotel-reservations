package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.ReservationsApp;
import com.example.pekarsky.reservations.dto.ReservationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static com.example.pekarsky.reservations.resources.ReservationsResource.RESERVATIONS_RESOURCE_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ReservationsApp.class)
@AutoConfigureMockMvc
public class ReservationsResourceIT {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testSuccessOnGettingAllReservations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_RESOURCE_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSuccessOnCreatingReservation() throws Exception {
        ReservationDto dto = prepareReservation();
        mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_RESOURCE_PATH)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSuccessCreateAndRetrieveReservation() throws Exception {
        ReservationDto dto = prepareReservation();
        MvcResult createReservationResult = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_RESOURCE_PATH)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String newReservationUrl = createReservationResult.getResponse().getContentAsString();
        MvcResult reservationResult = mvc.perform(MockMvcRequestBuilders.get(newReservationUrl)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        ReservationDto retrievedDto = asObject(reservationResult.getResponse().getContentAsString(), ReservationDto.class);
        Assertions.assertEquals(dto.getRoomNumber(), retrievedDto.getRoomNumber());
        Assertions.assertNotNull(retrievedDto.getId());
        Assertions.assertEquals(dto.getGuests(), retrievedDto.getGuests());
    }

    @Test
    public void testNotFoundOnGettingNonExistentReservationById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_RESOURCE_PATH + "/99999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testReserveDifferentRoomsAndGetOnlyByRoom() throws Exception {
        //reserve room 102
        ReservationDto dtoRoom102 = prepareReservation();
        dtoRoom102.setRoomNumber(102);
        mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_RESOURCE_PATH)
                .content(asJsonString(dtoRoom102))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //reserve room 202
        ReservationDto dtoRoom202 = prepareReservation();
        dtoRoom202.setRoomNumber(202);
        mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_RESOURCE_PATH)
                .content(asJsonString(dtoRoom202))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //once again
        dtoRoom202.setRoomNumber(202);
        mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_RESOURCE_PATH)
                .content(asJsonString(dtoRoom202))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        MvcResult reservationsByRoomResult = mvc.perform(MockMvcRequestBuilders.get(RESERVATIONS_RESOURCE_PATH + "/?roomNumber=202")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        ReservationDto[] reservationsByRoomNumber = asObject(reservationsByRoomResult.getResponse().getContentAsString(), ReservationDto[].class);

        Assertions.assertNotNull(reservationsByRoomNumber);
        Assertions.assertTrue(Arrays.stream(reservationsByRoomNumber).allMatch(r -> r.getRoomNumber().equals(202)));
        Assertions.assertEquals(2, reservationsByRoomNumber.length);
    }

    @Test
    public void testSuccessCreateAndDeleteReservation() throws Exception {
        ReservationDto dto = prepareReservation();
        MvcResult createReservationResult = mvc.perform(MockMvcRequestBuilders.post(RESERVATIONS_RESOURCE_PATH)
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String newReservationUrl = createReservationResult.getResponse().getContentAsString();
        mvc.perform(MockMvcRequestBuilders.delete(newReservationUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //deleted reservation cannot be retrieved
        mvc.perform(MockMvcRequestBuilders.get(newReservationUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        //delete to be idempotent
        mvc.perform(MockMvcRequestBuilders.delete(newReservationUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ReservationDto prepareReservation() {
        return ReservationDto.builder()
                .roomNumber(101)
                .startDate(LocalDate.of(2020, 3, 1))
                .endDate(LocalDate.of(2020, 3, 9))
                .guests(4)
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObject(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
