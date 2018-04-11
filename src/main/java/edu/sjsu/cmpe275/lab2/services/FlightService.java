package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.DTO.FlightDTO;
import edu.sjsu.cmpe275.lab2.DTO.PlaneDTO;

/**
 * Interface for Flight
 * Author: Hanisha Thirtham
 */
public interface FlightService {
    /**
     * Method to fetch the information about Flight and his/her reservation details.
     * @param id
     * @return Flight DTO
     */
    Object getFlight(String id);
    /**
     * Method to create a Flight for passenger.
     * @param price
     * @param from
     * @param to
     * @param departureTime
     * @param arrivalTime
     * @param description
     * @return Flight DTO
     */
     Object createFlight(String flightNumber,String price, String from, String to, String departureTime, String arrivalTime, String description, String capacity,
                               String model, String manufacturer, String year);

    /**
     * Method to update a Flight .
     * @param price
     * @param from
     * @param to
     * @param departureTime
     * @param arrivalTime
     * @param description
     * @return Flight DTO
     */
    Object updateFlight(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime, String description,
                                  String capacity,
                                  String model, String manufacturer, String year);

    /**
     * Method to delete the information about Flight and reservation details.
     * @param id
     * @return
     */
    Object deleteFlight(String id);

    /**
     * Method checks if the Flight exists or not.
     * @param id
     * @return Flight DTO
     */
    Object findFlightById(String id);
}

