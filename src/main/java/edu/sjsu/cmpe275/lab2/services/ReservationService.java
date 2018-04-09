package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface ReservationService {

    public ReservationDTO getReservation(Integer reservationNumber);

    public ResponseEntity<?> makeReservation(String passengerId, List<String> flights);

    public ResponseEntity<?> updateReservation(Integer reserVationNumber, List<String> flightAdded, List<String> flightRemoved);

    public void cancelReservation(Integer reservationNumber, ReservationDAO reservationDAO);
}
