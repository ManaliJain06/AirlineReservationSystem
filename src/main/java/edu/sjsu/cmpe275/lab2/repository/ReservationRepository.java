package edu.sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import edu.sjsu.cmpe275.lab2.DAO.*;

public interface ReservationRepository extends JpaRepository<ReservationDAO, Long> {
    ReservationDAO findByReservationnumber(Integer reservationnumber);

//    List<ReservationDAO> findByPassenger(Optional<PassengerDAO> passengerEntity);
    List<ReservationDAO> findByFlights(List<FlightDAO> flights);

    /*@Query("SELECT reservation FROM ReservationEntity reservation WHERE reservation.reservationnumber = :reservationnumber AND reservation.from = :from" +
            "" +
            "" +
            ""
            "")
    ReservationEntity findReservation()*/
}
