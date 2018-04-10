package edu.sjsu.cmpe275.lab2.TestControllers;

import edu.sjsu.cmpe275.lab2.DTO.FlightDTO;
import edu.sjsu.cmpe275.lab2.AirlineReservationSystemTest;
import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import edu.sjsu.cmpe275.lab2.controllers.FlightController;
import edu.sjsu.cmpe275.lab2.services.FlightService;
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

public class TestFlightController extends AirlineReservationSystemTest{

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    public void testGetFlighById() throws Exception{
        String id = "123";
        FlightDAO fightDAO = new FlightDAO();
        fightDAO.setFlightnumber("123");
        fightDAO.setDestination("Chicago");
        fightDAO.setOrigin("San Jose");

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightNumber("123");
        flightDTO.setDestination("Chicago");
        flightDTO.setOrigin("San Jose");

        Mockito.when(flightService.findFlightById(id)).thenReturn(fightDAO);
        Mockito.when(flightService.getFlight(id)).thenReturn(flightDTO);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/flight/{id}", 123)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(id)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(200, response.getStatus());
    }
}
