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
    @RequestMapping(value = "/flight/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createFlight(@PathVariable String id, @RequestParam String price, @RequestParam String from, @RequestParam String to, @RequestParam String departureTime,
                                          @RequestParam String arrivalTime, @RequestParam String description, @RequestParam String capacity,
                                          @RequestParam String model, @RequestParam String manufacturer, @RequestParam String year) {
        System.out.println("hitting here in /flight");
        System.out.println(id);
        // if(flightNumber.equalsIgnoreCase("flightNumber")){
        //create
        if (flightService.findFlightById(id) != null) {
            /*BadRequestDTO badRequest = BaseController.formBadRequest("400",
                    "Sorry, the flight with the same flight number already exists.");
            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);*/
            System.out.println("update");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            Object flight = flightService.updateFlight(id, price, from, to, departureTime, arrivalTime, description, capacity, model, manufacturer, year);
            if (flight != null) {
                return new ResponseEntity<>(flight, HttpStatus.OK);
            } else {
                BadRequestDTO badRequest = BaseController.formBadRequest("400", "Capacity cannot be updated to a number less than the number of reservations for the flight");
                return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            System.out.println("create");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            Object flight = flightService.createFlight(id, price, from, to, departureTime, arrivalTime, description, capacity, model, manufacturer, year);
        /*if (object instanceof BadRequestDTO) {
            System.out.println("hitting here in bad request of /flight");
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(object, httpHeaders, HttpStatus.NOT_FOUND);
        }*/
            if (flight != null) {
                return new ResponseEntity<>(flight, HttpStatus.OK);
            } else {
                BadRequestDTO badRequest = BaseController.formBadRequest("400", "Sorry, due to some issue, flight couldn't be created.");
                return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
            }
        }
        /*} else {
            // anything else means id inside db. hence, update
            System.out.println("update");
        }*/


    }
}





















