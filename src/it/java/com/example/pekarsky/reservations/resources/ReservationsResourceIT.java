package com.example.pekarsky.reservations.resources;

import com.example.pekarsky.reservations.ReservationsApp;
import com.example.pekarsky.reservations.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ReservationsApp.class)
@AutoConfigureMockMvc
public class ReservationsResourceIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testSuccessOnGettingAllReservations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/reservations/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
