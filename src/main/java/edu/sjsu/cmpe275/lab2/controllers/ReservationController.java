package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.services.PassengerService;
import edu.sjsu.cmpe275.lab2.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> makeReservation(@RequestParam(value="passengerId") String passengerId,
                                             @RequestParam(value="flightLists") List<String> flightLists) {


        ResponseEntity<?> responseEntity = reservationService.makeReservation(passengerId, flightLists);
        return responseEntity;
//        if (reservationDTO != null){
//            return new ResponseEntity<ReservationDTO>(reservationDTO, HttpStatus.OK);
//        }
//        else{
//            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
//                    "Passenger with id does not exist");
//            return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
//        }
    }
}
