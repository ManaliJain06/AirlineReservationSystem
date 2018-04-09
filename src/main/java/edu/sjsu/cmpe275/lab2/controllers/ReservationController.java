package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DTO.ResponseDTO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.repository.ReservationRepository;
import edu.sjsu.cmpe275.lab2.services.PassengerService;
import edu.sjsu.cmpe275.lab2.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.util.List;
import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private ReservationService reservationService;
    private ReservationRepository reservationRepository;

    @Autowired
    ReservationController(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> makeReservation(@RequestParam(value="passengerId") String passengerId,
                                             @RequestParam(value="flightLists") List<String> flightLists) {
        ResponseEntity<?> responseEntity = reservationService.makeReservation(passengerId, flightLists);
        return responseEntity;
    }


    @RequestMapping(value= "/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getReservation(@PathVariable String number) {
        ReservationDTO reservationDTO = reservationService.getReservation(Integer.parseInt(number));
        if(reservationDTO != null){
            return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
        } else{
            BadRequestDTO badRequest = BaseController.formBadRequest("404",
                    "Reserveration with number "+ number+" does not exist");
            return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{number}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateReservation(@PathVariable String number,
                                               @RequestParam(value="flightsAdded", required=false) List<String> flightsAdded,
                                               @RequestParam(value="flightsRemoved", required=false) List<String> flightsRemoved) {

        if(flightsAdded != null){
            if(flightsAdded.size() <= 0){
                BadRequestDTO badRequest = BaseController.formBadRequest("404",
                        "Flight Added value cannot be empty");
                return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
            }
        }
        if(flightsRemoved != null){
            if(flightsRemoved.size() <= 0){
                BadRequestDTO badRequest = BaseController.formBadRequest("404",
                        "Flight Remove value cannot be empty");
                return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
            }
        }

        ResponseEntity<?> response = reservationService.updateReservation(Integer.valueOf(number),
                flightsAdded,flightsRemoved);
        if(response == null){
            BadRequestDTO badRequest = BaseController.formBadRequest("404",
                    "No reservation record found");
            return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(value="/{number}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> cancelReservation(@PathVariable String number) {

        ReservationDAO reservation = reservationRepository.getByReservationnumber(Integer.parseInt(number));
        if(reservation != null){
            reservationService.cancelReservation(Integer.parseInt(number), reservation);
            ResponseDTO response = BaseController.formSuccessResponse("200",
                    "Reservation with id " + number +" is deleted successfully");
            HttpHeaders responseHeader = new HttpHeaders();
            responseHeader.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<>(response,responseHeader
                    , HttpStatus.OK);
        } else{
            BadRequestDTO badRequest = BaseController.formBadRequest("404",
                    "No reservation record found");
            return new ResponseEntity<>(badRequest, HttpStatus.NOT_FOUND);
        }
    }
}
