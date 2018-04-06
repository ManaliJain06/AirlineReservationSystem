package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.DTO.FlightDTO;
import edu.sjsu.cmpe275.lab2.DTO.PlaneDTO;

public interface FlightService {
    /**
     * This method is used to fetch the information about Flight and his/her reservation details.
     * @param id the Flight's Id
     * @return the Flight dto
     */
    public Object getFlight(String id);
    /**
     * This method is used to create a Flight w.r.t forwarded Flight's details.
     * @param price the Flight's price.
     * @param from the Flight's from date.
     * @param to the Flight's to date.
     * @param departureTime the Flight's departure time.
     * @param arrivalTime the Flight's arrival time.
     * @param description the Flight's description.
     * @return the Flight dto
     */
    public Object createFlight(String flightNumber,String price, String from, String to, String departureTime, String arrivalTime, String description, String capacity,
                               String model, String manufacturer, String year);

    /**
     * This method is used to update a Flight w.r.t forwarded Flight's details.
     * @param price the Flight's price.
     * @param from the Flight's from date.
     * @param to the Flight's to date.
     * @param departureTime the Flight's departure time.
     * @param arrivalTime the Flight's arrival time.
     * @param description the Flight's description.
     * @param plane the Flight's plane.
     * @return the Flight dto
     */
       public Object updateFlight(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime, String description,
                                  String capacity,
                                  String model, String manufacturer, String year);

    /**
     * This method is used to delete the information about Flight and his/her reservation details.
     * @param id the Flight's Id
     * @return the Long value indicating if the Flight is deleted or not.
     */
    //   public void deleteFlight(String id);

    /**
     * This method checks if the Flight exists or not.
     * @param id the Flight's Id
     * @return the Flight dto.
     */
    public Object findFlightById(String id);
}

