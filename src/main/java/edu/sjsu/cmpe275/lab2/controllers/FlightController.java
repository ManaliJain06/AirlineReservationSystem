package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.services.FlightService;
import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Transactional
@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    @RequestMapping(value = "/flight/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFlight(@RequestParam(value = "xml", required = false) String isXml,
                                       @PathVariable String id) {

        HttpHeaders responseHeaders = new HttpHeaders();
        if (isXml != null && isXml.equals("true")) {
            responseHeaders.setContentType(MediaType.APPLICATION_XML);
        } else {
            responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        System.out.println("test");
        if (flightService.findFlightById(id) != null) {
            System.out.println("inside not null");
            Object flight = flightService.getFlight(id);
            return new ResponseEntity<>(flight, responseHeaders, HttpStatus.OK);
        } else {

            BadRequestDTO badRequest = BaseController.formBadRequest("404",
                    "Sorry, the requested flight with id " + id + " does not exist.");

            return new ResponseEntity<>(badRequest, responseHeaders, HttpStatus.NOT_FOUND);
        }
    }
    //create flight
    @RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.POST)
    public ResponseEntity<?> createFlight(@PathVariable String flightNumber, @RequestParam String price, @RequestParam String from, @RequestParam String to, @RequestParam String departureTime,
                                          @RequestParam String arrivalTime, @RequestParam String description, @RequestParam String capacity,
                                          @RequestParam String model, @RequestParam String manufacturer, @RequestParam String year) {
        System.out.println("hitting here in /flight");
        System.out.println(flightNumber);
        // if(flightNumber.equalsIgnoreCase("flightNumber")){
        //create
        System.out.println("create");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        Object object = flightService.createFlight(price, from,to,departureTime,arrivalTime, description, capacity,model,manufacturer,year);
        if (object instanceof BadRequestDTO) {
            System.out.println("hitting here in bad request of /flight");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(object, httpHeaders, HttpStatus.NOT_FOUND);
        }

        /*} else {
            // anything else means id inside db. hence, update
            System.out.println("update");
        }*/

        return new ResponseEntity<>(object, httpHeaders, HttpStatus.OK);
    }
}





















