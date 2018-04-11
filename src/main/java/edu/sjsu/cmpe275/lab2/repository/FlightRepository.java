package edu.sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import java.util.List;

/**
 * Repository for connecting to flight database layer
 * Author: Hanisha Thirtham
 */
public interface FlightRepository extends JpaRepository<FlightDAO, String> {

    /**
     * Method to get flightnumber
     * @param flightnumber
     * @return FlightDAO
     */
    FlightDAO findByFlightnumber(String flightnumber);

    /**
     * Method to find flight by origin
     * @param origin
     * @return FlightDAO
     */
    List<FlightDAO> findByOrigin(String origin);

    /**
     * Method to find by destination
     * @param destination
     * @return FlightDAO
     */
    List<FlightDAO> findByDestination(String destination);
//    FlightDAO deleteByFlightnumber(Integer flightnumber);
}
