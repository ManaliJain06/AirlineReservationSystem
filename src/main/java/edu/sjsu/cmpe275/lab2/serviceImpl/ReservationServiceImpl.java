package edu.sjsu.cmpe275.lab2.serviceImpl;
import ch.qos.logback.core.CoreConstants;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.MessageHeader;
import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.controllers.BaseController;
import edu.sjsu.cmpe275.lab2.repository.PassengerRepository;
import edu.sjsu.cmpe275.lab2.repository.ReservationRepository;
import edu.sjsu.cmpe275.lab2.repository.FlightRepository;
import edu.sjsu.cmpe275.lab2.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.logging.Logger;

@Service
public class ReservationServiceImpl implements ReservationService {

    Logger log =  Logger.getLogger(ReservationServiceImpl.class.getName());
    private ReservationRepository reservationRepository;

    private PassengerRepository passengerRespository;

    private FlightRepository flightRespository;

    @Autowired
    ReservationServiceImpl(ReservationRepository reservationRepository,
                           PassengerRepository passengerRespository, FlightRepository flightRespository){
        this.reservationRepository = reservationRepository;
        this.passengerRespository = passengerRespository;
        this.flightRespository = flightRespository;
    }

    @Override
    public ReservationDTO getReservation(Integer reservationNumber) {
        ReservationDAO reservationDAO = reservationRepository.getByReservationnumber(reservationNumber);
        if(reservationDAO != null){
            ReservationDTO reservation = BaseServiceImpl.mapReservationDAOToDTO(reservationDAO,
                    reservationDAO.getPrice());
            return reservation;
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<?> makeReservation(String passengerId, List<String> flights){
        PassengerDAO passengerDAO = passengerRespository.getById(Integer.valueOf(passengerId));
        if(passengerDAO != null){
            // getting the DAO of all the flights which are to be booked
            List<FlightDAO> flightsToBeBookedList = new ArrayList<>();
            for(String flightNumber: flights){
                FlightDAO flight = flightRespository.findByFlightnumber(flightNumber);
                if(flight!= null){
                    if(flight.getSeatsleft() != 0) {
                        flightsToBeBookedList.add(flight);
                    } else{
                        BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                                "Flight "+ flightNumber +" has no seats left");
                        return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
                    }
                } else{
                    BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                            "Flight "+ flightNumber +"does not exists in the system");
                    return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
                }
            }

            // getting all the flights previously booked by the user
            List<ReservationDAO> reservationsOfPassengers = passengerDAO.getReservationsOfPassengers();
            List<FlightDAO> allBookedFlights = new ArrayList<>();
            for(ReservationDAO reservations: reservationsOfPassengers){
                List<FlightDAO> eachFlightReservationList = reservations.getFights();
                allBookedFlights.addAll(eachFlightReservationList);
            }

            // checking whether the flights which are to be booked has time collision with
            // the already booked flights
            boolean timeOverlapped = false;
            for(FlightDAO booked : allBookedFlights){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date startTime  = sdf.parse(booked.getDeparturetime());
                    Date endTime  = sdf.parse(booked.getArrivaltime());
                    for(FlightDAO toBook : flightsToBeBookedList){
                        Date bookStartTime  = sdf.parse(toBook.getDeparturetime());
                        Date bookEndTime  = sdf.parse(toBook.getArrivaltime());
                        if((startTime.after(bookStartTime) && endTime.before(bookEndTime))
                                || (bookStartTime.after(startTime) && bookEndTime.before(endTime))
                                || (bookStartTime.before(startTime) && bookEndTime.before(endTime) && bookEndTime.after(startTime))
                                || (bookStartTime.after(startTime) && bookEndTime.after(endTime) && bookStartTime.before(endTime))){
                            timeOverlapped = true;
                            break;
                        }
                    }
                   if(timeOverlapped){
                        break;
                   }
                } catch (ParseException p){
                    log.info("parser exception while parsing dates");
                }
            }
            // also to check whether the flights list which are added for reservations has time
            // collision in them
            for(int i=0; i<flightsToBeBookedList.size(); i++){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date startTime  = sdf.parse(flightsToBeBookedList.get(i).getDeparturetime());
                    Date endTime  = sdf.parse(flightsToBeBookedList.get(i).getArrivaltime());
                    for(int j=i+1 ; j<flightsToBeBookedList.size(); j++){
                        Date bookStartTime  = sdf.parse(flightsToBeBookedList.get(j).getDeparturetime());
                        Date bookEndTime  = sdf.parse(flightsToBeBookedList.get(j).getArrivaltime());
                        if((startTime.after(bookStartTime) && endTime.before(bookEndTime))
                                || (bookStartTime.after(startTime) && bookEndTime.before(endTime))
                                || (bookStartTime.before(startTime) && bookEndTime.before(endTime) && bookEndTime.after(startTime))
                                || (bookStartTime.after(startTime) && bookEndTime.after(endTime) && bookStartTime.before(endTime))){
                            timeOverlapped = true;
                            break;
                        }
                    }
                    if(timeOverlapped){
                        break;
                    }
                } catch (ParseException p){
                    log.info("parser exception while parsing dates");
                }
            }

            ReservationDTO reservationDTO = null;
            if(!timeOverlapped){
                //getting total prices of the flights to be booked
                Double totalPrice = 0.0;
                for(FlightDAO toBook : flightsToBeBookedList){
                    totalPrice = totalPrice + toBook.getPrice();
                }
                ReservationDAO reservationDAO = new ReservationDAO(passengerDAO, totalPrice, flightsToBeBookedList);
                ReservationDAO resultDAO = reservationRepository.save(reservationDAO);
               if(resultDAO != null){
                   // update the seats in the flights which are booked
                   for(FlightDAO toBook : flightsToBeBookedList){
                       toBook.setSeatsleft(toBook.getSeatsleft()-1);
                       flightRespository.save(toBook);
                   }
                   reservationDTO = BaseServiceImpl.mapReservationDAOToDTO(resultDAO, totalPrice);
               }
            } else{
                BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                        "Time overlapping with previously booked flights");
                return new ResponseEntity<BadRequestDTO>(badRequestDTO, HttpStatus.NOT_FOUND);
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<>(reservationDTO,httpHeaders, HttpStatus.OK);
        } else{
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                    "Passenger does not exist in the system");
            return new ResponseEntity<BadRequestDTO>(badRequestDTO, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updateReservation(Integer reservationNumber, List<String> flightAdded, List<String> flightRemoved){
        ReservationDAO reservationDAO = reservationRepository.getByReservationnumber(reservationNumber);
        HashSet<FlightDAO> flightAgainstReservation = new HashSet<>();

        if(reservationDAO != null){
            flightAgainstReservation.addAll(reservationDAO.getFights());
            // get all the flights from all the previous reservations of the user who did the current provided
            // reservation
            int id = reservationDAO.getPassenger().getId();
            PassengerDAO passenger = passengerRespository.getById(id);
            List<ReservationDAO> allReservations = passenger.getReservationsOfPassengers();

            //getting all flights of the passenger to be checked against the files to be added
            List<FlightDAO> allFlights = new ArrayList<>();
            for(ReservationDAO res: allReservations){
                allFlights.addAll(res.getFights());
            }

            //check if the parameter to add flight is present and if present then
            // getting the DAO of all the flights which are to be booked
            List<FlightDAO> flightsToBeAddList = new ArrayList<>();
            if(flightAdded != null){
                for(String flightNumber: flightAdded){
                    FlightDAO flight = flightRespository.findByFlightnumber(flightNumber);
                    if(flight!= null){
                        if(flight.getSeatsleft() != 0) {
                            flightsToBeAddList.add(flight);
                        } else{
                            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                                    "Flight to add "+ flightNumber +" has no seats left");
                            return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
                        }
                    } else{
                        BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                                "Flight to add "+ flightNumber +"does not exists in the system");
                        return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
                    }
                }
            }

            //check if the parameter to remove flight is present and if present then
            // getting the DAO of all the flights which are to be removed

            List<FlightDAO> flightsToBeRemoveList = new ArrayList<>();
            if(flightRemoved != null){
                for(String flightNumber: flightRemoved){
                    FlightDAO flight = flightRespository.findByFlightnumber(flightNumber);
                    if(flight!= null)
                        if(flightAgainstReservation.contains(flight)) {
                            flightsToBeRemoveList.add(flight);
                        } else{
                            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                                    "Flight "+ flightNumber +" was not in the reservation list of flights so cannot remove ");
                            return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
                    } else{
                        BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                                "Flight to remove "+ flightNumber +"does not exists in the system");
                        return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
                    }
                }
            }

            allFlights.removeAll(flightsToBeRemoveList);
            //checking the time overlap of the flights to be added and already booked flights by the user
            boolean isTimeOverlapped = false;
            for(FlightDAO alreadyBooked : allFlights){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date startTime  = dateFormat.parse(alreadyBooked.getDeparturetime());
                    Date endTime  = dateFormat.parse(alreadyBooked.getArrivaltime());
                    for(FlightDAO toAdd : flightsToBeAddList){
                        Date bookStartTime  = dateFormat.parse(toAdd.getDeparturetime());
                        Date bookEndTime  = dateFormat.parse(toAdd.getArrivaltime());
                        if((bookStartTime.after(startTime) && bookEndTime.before(endTime)
                                || (startTime.after(bookStartTime) && endTime.before(bookEndTime)))
                                || (bookStartTime.before(startTime) && bookEndTime.before(endTime) && bookEndTime.after(startTime))
                                || (bookStartTime.after(startTime) && bookEndTime.after(endTime) && bookStartTime.before(endTime))){
                            isTimeOverlapped = true;
                            break;
                        }
                    }
                    if(isTimeOverlapped){
                        break;
                    }
                } catch (ParseException p){
                    log.info("parser exception while parsing dates");
                }
            }

            for(int i=0; i<flightsToBeAddList.size(); i++){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date startTime  = sdf.parse(flightsToBeAddList.get(i).getDeparturetime());
                    Date endTime  = sdf.parse(flightsToBeAddList.get(i).getArrivaltime());
                    for(int j=i+1 ; j<flightsToBeAddList.size(); j++){
                        Date bookStartTime  = sdf.parse(flightsToBeAddList.get(j).getDeparturetime());
                        Date bookEndTime  = sdf.parse(flightsToBeAddList.get(j).getArrivaltime());
                        if((startTime.after(bookStartTime) && endTime.before(bookEndTime))
                                || (bookStartTime.after(startTime) && bookEndTime.before(endTime))
                                || (bookStartTime.before(startTime) && bookEndTime.before(endTime) && bookEndTime.after(startTime))
                                || (bookStartTime.after(startTime) && bookEndTime.after(endTime) && bookStartTime.before(endTime))){
                            isTimeOverlapped = true;
                            break;
                        }
                    }
                    if(isTimeOverlapped){
                        break;
                    }
                } catch (ParseException p){
                    log.info("parser exception while parsing dates");
                }
            }

            flightAgainstReservation.addAll(flightsToBeAddList);
            flightAgainstReservation.removeAll(flightsToBeRemoveList);
            Double newUpdatedPrice = 0.0;
            ReservationDAO updatedReservation;
            ReservationDTO reservationDTO = null;
            if(!isTimeOverlapped){
                List<FlightDAO> flightsToUpdate = new ArrayList<>();
                for(FlightDAO f: flightAgainstReservation){
                    newUpdatedPrice = newUpdatedPrice + f.getPrice();
                    flightsToUpdate.add(f);
                }
                reservationDAO.setPrice(newUpdatedPrice);
                reservationDAO.setFlights(flightsToUpdate);
                reservationDAO.setPassenger(passenger);
                reservationDAO.setReservationnumber(reservationDAO.getReservationnumber());
                updatedReservation = reservationRepository.save(reservationDAO);
                if(updatedReservation != null){
                    // update the seats in the flights which are added
                    for(FlightDAO addedFlights : flightsToBeAddList){
                        addedFlights.setSeatsleft(addedFlights.getSeatsleft()-1);
                        flightRespository.save(addedFlights);
                    }
                    // update the seats in the flights which are removed
                    for(FlightDAO addedFlights : flightsToBeRemoveList){
                        addedFlights.setSeatsleft(addedFlights.getSeatsleft()+1);
                        flightRespository.save(addedFlights);
                    }
                    reservationDTO = BaseServiceImpl.mapReservationDAOToDTO(updatedReservation, newUpdatedPrice);
                }
            } else{
                BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
                        "Time overlapping with previously booked flights with the flights to be added");
                return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(reservationDTO,HttpStatus.OK);
        } else{
            return null;
        }
    }

    @Override
    public void cancelReservation(final Integer reservationNumber, final ReservationDAO reservationDAO) {

        int deleted = reservationRepository.deleteByReservationnumber(reservationNumber);
        if(deleted == 1){
           List<FlightDAO> flights = reservationDAO.getFights();
            for(FlightDAO flight: flights){
                FlightDAO flightToUpdate = flight;
                flightToUpdate.setSeatsleft(flight.getSeatsleft()+1);
                flightRespository.save(flightToUpdate);
           }
        }
    }

    @Override
    public ResponseEntity searchReservation(Integer passengerId, String origin, String to, Integer flightNumber){
        // check if only passengerId is provided then search for all the reservation of that passenger and
        // return data
        if(passengerId != null && origin == null && to == null && flightNumber == null){
            PassengerDAO passenger  = passengerRespository.getById(passengerId);

//            ReservationDAO reservationDAO = reservationRepository.getByReservationnumber(reservationNumber);
        }
        return null;
    }
}
