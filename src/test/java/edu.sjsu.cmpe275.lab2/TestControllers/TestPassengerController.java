package edu.sjsu.cmpe275.lab2.TestControllers;


import edu.sjsu.cmpe275.lab2.AirlineReservationSystemTest;
import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.services.PassengerService;
import edu.sjsu.cmpe275.lab2.controllers.PassengerController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;

public class TestPassengerController extends AirlineReservationSystemTest{

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerController passengerController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();
    }

    @Test
    public void testGetPassenger() throws Exception{
        String passengerId = "2";
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setId("2");
        passengerDTO.setAge("23");
        passengerDTO.setFirstname("Manali");
        passengerDTO.setLastname("Jain");
        passengerDTO.setGender("Female");
        Mockito.when(passengerService.getPassenger(Integer.valueOf(passengerId))).thenReturn(passengerDTO);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/passenger/{id}", 2)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(passengerId)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testCreatePassenger() throws Exception{

        String firstName = "Manali";
        String lastname = "Jain";
        String age = "23";
        String gender = "female";
        String phone = "123456789";

        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setId("3");
        passengerDTO.setAge("23");
        passengerDTO.setFirstname("Manali");
        passengerDTO.setLastname("Jain");
        passengerDTO.setGender("Female");
        Mockito.when(passengerService.createPassenger(
                firstName, lastname, Integer.valueOf(age), gender, phone)).thenReturn(passengerDTO);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/passenger")
                .param("firstname", firstName)
                .param("lastname", lastname)
                .param("age", age)
                .param("gender", gender)
                .param("phone", phone)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testCreatePassengerFaliure() throws Exception{

        String firstName = "Manali";
        String lastname = "Jain";
        String age = "23";
        String gender = "female";
        String phone = "123456789";
        PassengerDTO passengerDTO = null;

        Mockito.when(passengerService.createPassenger(
                firstName, lastname, Integer.valueOf(age), gender, phone)).thenReturn(passengerDTO);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/passenger")
                .param("firstname", firstName)
                .param("lastname", lastname)
                .param("age", age)
                .param("gender", gender)
                .param("phone", phone)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(404, response.getStatus());
    }
}
