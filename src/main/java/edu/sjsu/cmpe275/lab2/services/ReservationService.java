package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.DTO.Reservations;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface ReservationService {

    ReservationDTO getReservation(Integer reservationNumber);

    ResponseEntity<?> makeReservation(String passengerId, List<String> flights);

    ResponseEntity<?> updateReservation(Integer reservationNumber, List<String> flightAdded, List<String> flightRemoved);

    void cancelReservation(Integer reservationNumber, ReservationDAO reservationDAO);

    ResponseEntity<?> searchReservation(Integer passengerId, String origin, String to, String flightNumber);
}
