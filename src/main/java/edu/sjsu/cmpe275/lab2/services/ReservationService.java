package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.DTO.Reservations;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface for Reservation
 * Author: Manali Jain
 */
public interface ReservationService {

    /**
     * Interface to get a reservation
     * @param reservationNumber
     * @return ReservationDTO
     */
    ReservationDTO getReservation(Integer reservationNumber);

    /**
     * Interface to create a reservation
     * @param passengerId
     * @param flights
     * @return ResponseEntity
     */
    ResponseEntity<?> makeReservation(String passengerId, List<String> flights);

    /**
     * Interface to update a reservation
     * @param reservationNumber
     * @param flightAdded
     * @param flightRemoved
     * @return ResponseEntity
     */
    ResponseEntity<?> updateReservation(Integer reservationNumber, List<String> flightAdded, List<String> flightRemoved);

    /**
     * Interface to cancel a reservation
     * @param reservationNumber
     * @param reservationDAO
     */
    void cancelReservation(Integer reservationNumber, ReservationDAO reservationDAO);

    /**
     * Interfac to search a reservation
     * @param passengerId
     * @param origin
     * @param to
     * @param flightNumber
     * @return
     */
    ResponseEntity<?> searchReservation(Integer passengerId, String origin, String to, String flightNumber);
}
