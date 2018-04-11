package edu.sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import edu.sjsu.cmpe275.lab2.DAO.*;

/**
 * Repository for Reservation
 * Author: Manali Jain
 */
public interface ReservationRepository extends JpaRepository<ReservationDAO, Long> {
    /**
     * to get reservation number
     * @param reservationnumber
     * @return
     */
    ReservationDAO getByReservationnumber(Integer reservationnumber);

    /**
     * to delete reservation by number
     * @param number
     * @return
     */
    int deleteByReservationnumber(int number);

}
