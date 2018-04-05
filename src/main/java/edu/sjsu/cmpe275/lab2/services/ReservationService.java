package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;

public interface ReservationService {

    public ReservationDTO getReservation(String reservationNumber);

    public ReservationDTO makeReservation(String passengerId, String flights);

}
