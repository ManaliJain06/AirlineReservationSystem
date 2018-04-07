package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface ReservationService {

    public ReservationDTO getReservation(String reservationNumber);

    public ResponseEntity<?> makeReservation(String passengerId, List<String> flights);

}
