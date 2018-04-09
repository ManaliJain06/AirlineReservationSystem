package edu.sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import edu.sjsu.cmpe275.lab2.DAO.*;

public interface ReservationRepository extends JpaRepository<ReservationDAO, Long> {
    ReservationDAO getByReservationnumber(Integer reservationnumber);

    int deleteByReservationnumber(int number);

}
