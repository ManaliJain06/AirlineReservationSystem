package edu.sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import java.util.List;

public interface FlightRepository extends JpaRepository<FlightDAO, String> {

    FlightDAO findByFlightnumber(String flightnumber);
    List<FlightDAO> findByOrigin(String origin);
    List<FlightDAO> findByDestination(String destination);
    FlightDAO deleteByFlightnumber(Integer flightnumber);

    /*ReservationEntity deleteReservationEntityByReservationnumber(Integer reservationnumber);
    int deleteByReservationnumber(Integer reservationnumber);*/

//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE FlightEntity f SET f.seatsleft = :seatsleft WHERE f.flightnumber = :flightnumber")
//    int updateSeatsLeft(@Param("seatsleft") Long seatsleft, @Param("flightnumber") Long flightnumber);
}
