package edu.sjsu.cmpe275.lab2.TestControllers;

import edu.sjsu.cmpe275.lab2.AirlineReservationSystemTest;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.controllers.ReservationController;
import edu.sjsu.cmpe275.lab2.repository.ReservationRepository;
import edu.sjsu.cmpe275.lab2.services.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

public class TestReservationController extends AirlineReservationSystemTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationController reservationController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testMakeReservation() throws Exception{
        String passengerId = "2";
        List<String> flightLists = new ArrayList<>();
        flightLists.add("A123");
        flightLists.add("A345");

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationNumber("123");

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<?> response =  new ResponseEntity<>(reservationDTO, header, HttpStatus.OK);

//        Mockito.when(reservationService.makeReservation(passengerId, flightLists))
//                .thenReturn(response);

        doReturn(response).when(reservationService).makeReservation(passengerId, flightLists);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/reservation")
                .param("passengerId", passengerId)
                .param("flightLists", flightLists.toArray(new String[flightLists.size()]))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse servletResponse = result.getResponse();

        assertEquals(200, servletResponse.getStatus());
    }
}