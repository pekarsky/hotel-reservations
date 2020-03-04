package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.ReservationsApp;
import com.example.pekarsky.reservations.dto.ReservationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        mvc.perform(MockMvcRequestBuilders.get("/reservations/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSuccessOnCreatingReservation() throws Exception {
        ReservationDto dto = ReservationDto.builder().roomNumber(101).guests(4).build();
        mvc.perform(MockMvcRequestBuilders.post("/reservations/")
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSuccessCreateAndRetrieveReservation() throws Exception {
        ReservationDto dto = ReservationDto.builder().roomNumber(101).guests(4).build();
        MvcResult createReservationResult = mvc.perform(MockMvcRequestBuilders.post("/reservations/")
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String newReservationUrl = createReservationResult.getResponse().getContentAsString();
        MvcResult reservationResult = mvc.perform(MockMvcRequestBuilders.get(newReservationUrl)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        ReservationDto retrievedDto = asObject(reservationResult.getResponse().getContentAsString(), ReservationDto.class);
        assertEquals(dto.getRoomNumber(), retrievedDto.getRoomNumber());
        assertNotNull(retrievedDto.getId());
        assertEquals(dto.getGuests(), retrievedDto.getGuests());
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