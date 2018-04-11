package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.services.FlightService;
import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DTO.FlightDTO;
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

/**
 * Controller for Flight APIS
 * Author: Hanisha Thirtham
 */
@Transactional
@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    /**
     * Rest API for getting flight
     * @param isXML
     * @param id
     * @return ResponseEntity object
     */
    @RequestMapping(value = "/flight/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getFlight(@RequestParam(value = "xml", required = false) String isXML,
                                       @PathVariable String id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        if(isXML == null) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        } else if(isXML.equals("true")){
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
        } else {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        }

        if (flightService.findFlightById(id) != null) {
            Object flight = flightService.getFlight(id);
            return new ResponseEntity<>(flight, httpHeaders, HttpStatus.OK);
        } else {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            BadRequestDTO badRequest = BaseController.formBadRequest("404",
                    "Sorry, the requested flight with id " + id + " does not exist.");
            return new ResponseEntity<>(badRequest, httpHeaders, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Rest API for creating flight
     * @param id
     * @param price
     * @param origin
     * @param destination
     * @param departureTime
     * @param arrivalTime
     * @param description
     * @param capacity
     * @param model
     * @param manufacturer
     * @param year
     * @return ResponseEntity object
     */
    //create flight
    @RequestMapping(value = "/flight/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createFlight(@PathVariable String id, @RequestParam String price, @RequestParam String origin, @RequestParam String destination, @RequestParam String departureTime,
                                          @RequestParam String arrivalTime, @RequestParam String description, @RequestParam String capacity,
                                          @RequestParam String model, @RequestParam String manufacturer, @RequestParam String year) {
        // if(flightNumber.equalsIgnoreCase("flightNumber")){
        //create
        if (flightService.findFlightById(id) != null) {
            /*BadRequestDTO badRequest = BaseController.formBadRequest("400",
                    "Sorry, the flight with the same flight number already exists.");
            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);*/
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            Object flight = flightService.updateFlight(id, price, origin, destination, departureTime, arrivalTime, description, capacity, model, manufacturer, year);

            /*if (flight != null) {
                return new ResponseEntity<>(flight, HttpStatus.OK);
            } else {
                BadRequestDTO badRequest = BaseController.formBadRequest("400", "Capacity cannot be updated to a number less than the number of reservations for the flight");
                return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
            }*/
            if (flight instanceof FlightDTO) {
                httpHeaders.setContentType(MediaType.APPLICATION_XML);
                return new ResponseEntity<>(flight,httpHeaders, HttpStatus.OK);
            }
            else{
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(flight, httpHeaders, HttpStatus.NOT_FOUND);
            }
                //(flight != null) {
            }
        else {
            HttpHeaders httpHeaders = new HttpHeaders();
            Object flight = flightService.createFlight(id, price, origin, destination, departureTime, arrivalTime, description, capacity, model, manufacturer, year);
            if (flight instanceof BadRequestDTO) {
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return new ResponseEntity<>(flight, httpHeaders, HttpStatus.NOT_FOUND);
            } else{
            //(flight != null) {
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<>(flight,httpHeaders, HttpStatus.OK);
            }/* else {
                BadRequestDTO badRequest = BaseController.formBadRequest("400", "Sorry, due to some issue, flight couldn't be created.");
                return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
            }*/
        }
        /*} else {
            // anything else means id inside db. hence, update
        }*/
    }

    /**
     * Rest API for deleting flight
     * @param id
     * @return ResponseEntity object
     */
    @RequestMapping(value = "/airline/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFLight(@PathVariable String id)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        Object object = flightService.deleteFlight(id);
        if(object instanceof BadRequestDTO)
        {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(object, httpHeaders, HttpStatus.NOT_FOUND);
        }
        httpHeaders.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(object, httpHeaders, HttpStatus.OK);
    }
}





















