package edu.sjsu.cmpe275.lab2.serviceImpl;
import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.controllers.BaseController;
import edu.sjsu.cmpe275.lab2.repository.PassengerRepository;
import edu.sjsu.cmpe275.lab2.repository.ReservationRepository;
import edu.sjsu.cmpe275.lab2.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired PassengerRepository passengerRespository;

    ReservationServiceImpl(ReservationRepository reservationRepository,
                           PassengerRepository passengerRespository){
        this.reservationRepository = reservationRepository;
        this.passengerRespository = passengerRespository;
    }

    @Override
    public ReservationDTO getReservation(String reservationNumber){
        return null;
    }

    @Override
    public ResponseEntity<?> makeReservation(String passengerId, List<String> flights){
        PassengerDAO passengerDAO = passengerRespository.getById(Integer.valueOf(passengerId));
        if(passengerDAO != null){

            List<ReservationDAO> reservationsOfPassengers = passengerDAO.getReservationsOfPassengers();

//            FlightDAO flightDAO =
            return null;
        } else{
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                    "Passenger does not exist in the system");
            return new ResponseEntity<BadRequestDTO>(badRequestDTO, HttpStatus.NOT_FOUND);
        }
    }
}
