package edu.sjsu.cmpe275.lab2.serviceImpl;

import edu.sjsu.cmpe275.lab2.controllers.BaseController;
import edu.sjsu.cmpe275.lab2.repository.FlightRepository;
import edu.sjsu.cmpe275.lab2.repository.ReservationRepository;
import edu.sjsu.cmpe275.lab2.repository.PassengerRepository;
import edu.sjsu.cmpe275.lab2.DAO.*;
import edu.sjsu.cmpe275.lab2.DTO.*;
import edu.sjsu.cmpe275.lab2.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Serivce for Flight methods
 * Author: Hanisha Thirtham
 */
@Service
public class FlightServiceImpl implements FlightService {

    Logger log =  Logger.getLogger(FlightServiceImpl.class.getName());
    @Autowired
    private FlightRepository FlightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * When flights are updated, this function checks if the current flight being updated
     * and the flights already reserved are overlapping for a passenger
     */
    private Boolean timesOverlap(Set<FlightDAO> previouslyBookedFlights, FlightDAO flightToBeUpdated){
        Date toUpdateStartTime = new Date();
        Date toUpdateEndTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            toUpdateStartTime = dateFormat.parse(flightToBeUpdated.getDeparturetime());
            toUpdateEndTime = dateFormat.parse(flightToBeUpdated.getArrivaltime());
        } catch (ParseException p) {
            log.info("parser exception while parsing dates");
        }


        for(FlightDAO alreadyBooked :previouslyBookedFlights)
        {
            try {
                Date startTime = dateFormat.parse(alreadyBooked.getDeparturetime());
                Date endTime = dateFormat.parse(alreadyBooked.getArrivaltime());

                if ((toUpdateStartTime.after(startTime) && toUpdateEndTime.before(endTime)
                        || (startTime.after(toUpdateStartTime) && endTime.before(toUpdateEndTime)))
                        || (toUpdateStartTime.before(startTime) && toUpdateEndTime.before(endTime) && toUpdateEndTime.after(startTime))
                        || (toUpdateStartTime.after(startTime) && toUpdateEndTime.after(endTime) && toUpdateStartTime.before(endTime))) {
                    return true;
                }
            } catch (ParseException p) {
                log.info("parser exception while parsing dates");
            }
        }
        return false;
    }

    /**
     * This method is used to fetch the information about Passenger and his/her
     * reservation details.
     *
     * @param id
     * @return Flight DTO
     */

    @Override
    public Object getFlight(String id) {

        FlightDAO flightDAO = FlightRepository.findByFlightnumber(id);
        PlaneDAO planeDAO;
        if(flightDAO == null)
        {
            String msg = "Flight with number " + id + " does not exist";
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",msg);
            return badRequestDTO;
        }
        //list of reservations
        //list of passengers(input reservations as parameter

        List<ReservationDAO> reservationEntities = flightDAO.getReservations();
        List<PassengerDAO> passengerDAO = new ArrayList<>();
        for(ReservationDAO reservationDAO: reservationEntities)
        {
            passengerDAO.add(reservationDAO.getPassenger());
        }

        List<PassengerDTO> passengerList = new ArrayList<>();

        for(PassengerDAO passenger: passengerDAO)
        {
            passengerList.add(BaseServiceImpl.mapPassengerDAOtoDTO(passenger));
        }

        PlaneDTO plane = new PlaneDTO();

        PlaneDTO plane1 = new PlaneDTO(flightDAO.getPlaneDAO().getCapacity().toString(),
                flightDAO.getPlaneDAO().getModel(),
                flightDAO.getPlaneDAO().getManufacturer(),
                flightDAO.getPlaneDAO().getYear());
        FlightDTO flight = new FlightDTO(flightDAO.getFlightnumber(),
                flightDAO.getPrice().toString(),
                flightDAO.getOrigin(),
                flightDAO.getDestination(),
                flightDAO.getDeparturetime(),
                flightDAO.getArrivaltime(),
                flightDAO.getDescription(),
                flightDAO.getSeatsleft().toString(),
                plane1,
                passengerList);

        return flight;
    }

    /**
     * Method to create a flight
     * @param flightNumber
     * @param price the Flight's price.
     * @param origin
     * @param destination
     * @param departureTime the Flight's departure time.
     * @param arrivalTime the Flight's arrival time.
     * @param description the Flight's description.
     * @param capacity
     * @param model
     * @param manufacturer
     * @param year
     * @return FLight DTO
     */
    @Override
    public Object createFlight(String flightNumber, String price, String origin, String destination,
                               String departureTime, String arrivalTime, String description, String capacity,
                               String model, String manufacturer, String year) {
        String seatsLeft = capacity;
        PlaneDAO planeDAO = new PlaneDAO(Long.parseLong(capacity),model,manufacturer,year);
            FlightDAO flightDAO = new FlightDAO(flightNumber,Double.parseDouble(price), origin, destination,
                    departureTime,arrivalTime, Long.valueOf(seatsLeft),
                    description, planeDAO);
        flightDAO = FlightRepository.save(flightDAO);

        if(flightDAO != null) {
            PlaneDTO plane1 = new PlaneDTO(flightDAO.getPlaneDAO().getCapacity().toString(),
                    flightDAO.getPlaneDAO().getModel(),
                    flightDAO.getPlaneDAO().getManufacturer(),
                    flightDAO.getPlaneDAO().getYear());
            List<PassengerDTO> passengerList = new ArrayList<>();  //flight has just been created, hence the passengers list will be empty
            Passengers passengers = new Passengers(passengerList);
            FlightDTO flight = new FlightDTO(flightDAO.getFlightnumber(),
                    flightDAO.getPrice().toString(),
                    flightDAO.getOrigin(),
                    flightDAO.getDestination(),
                    flightDAO.getDeparturetime(),
                    flightDAO.getArrivaltime(),
                    flightDAO.getDescription(),
                    flightDAO.getSeatsleft().toString(),
                    plane1,
                    passengerList);

            return flight;
        }
        else{
            System.out.println("hello creation of the flight could not be successful:" );
            String msg = "creation of the flight could not be successful" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",msg);
            return badRequestDTO;
        }
    }

    /**
     * Merhod to update a flight
     * @param flightNumber
     * @param price the Flight's price.
     * @param origin
     * @param destination
     * @param departureTime the Flight's departure time.
     * @param arrivalTime the Flight's arrival time.
     * @param description the Flight's description.
     * @param capacity
     * @param model
     * @param manufacturer
     * @param year
     * @return Flight DTO
     */
    @Override
    public Object updateFlight(String flightNumber,String price, String origin, String destination, String departureTime, String arrivalTime, String description,
                               String capacity,
                               String model, String manufacturer, String year) {

       // long passengerId = Long.parseLong(id);
      //   long Capacity ;
        FlightDAO flightEntity = FlightRepository.findByFlightnumber(flightNumber);
//        Set<FlightDAO> flightToBeUpdated = new HashSet<>();
        //flightToBeUpdated.add(flightEntity);
        List<ReservationDAO> reservationEntities = flightEntity.getReservations();
        // update the details.
       // long seatsLeft ;
        Set<FlightDAO> previouslyBookedFlights = new HashSet<>();
        for (ReservationDAO reservationEntity : reservationEntities) {
            PassengerDAO passengerEntity = reservationEntity.getPassenger();
            List<ReservationDAO> reservationsOfPassengers = passengerEntity.getReservationsOfPassengers();
           // List<FlightDAO> currentReservedFlights=reservationEntity.getFlights();
            for(ReservationDAO reservationsOfPassengersEntity: reservationsOfPassengers) //changed yesterday
                previouslyBookedFlights.addAll(reservationsOfPassengersEntity.getFights());
        }
        int size=0; // to calculate the size of the list reservationEntities, to find the number of reservations for that flight.
        for (ReservationDAO reservationEntity : reservationEntities) {
           size++;
        }
        if(flightEntity !=null) {
        if(Integer.parseInt(capacity) < size){  //if capapcity id less than the number of reservations throw 400 error.
            System.out.println("hello capacity is less than number of reservations for that particular flight:" + size);
            String msg = "Capacity cannot be updated to a number less than the number of reservations for the flight" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
            return badRequestDTO;
        }

        else {
            System.out.println("hello flightEntitiy:" + flightEntity);
            PlaneDAO planeEntity = new PlaneDAO(Long.parseLong(capacity), model, manufacturer, year);
            long Capacity = flightEntity.getPlaneDAO().getCapacity(); //capacity there in the particular flight before update
            long seatsLeft =flightEntity.getSeatsleft();
            long capacityDiff = Long.parseLong(capacity)-Capacity;         //   long SeatsLeft = Long.valueOf(seatsLeft);
               if(capacityDiff< 0 && seatsLeft==0 ) //if seatsLeft is zero and difference is negative, the seatsLEft will be updated to negative, hence exception will be thrown
               {
                   String msg = "seats left error" ;
                   BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
                   return badRequestDTO;
                  }
               else{
                   seatsLeft = Long.valueOf(seatsLeft) + capacityDiff; //i update capacity to 10 from 5, seatsLeft is changed from 5 to 0
               }
            /*
            to compare the overlap times for previosly booked flights and the updated entity.
            */
            PlaneDAO newPlaneEntity = new PlaneDAO(Long.parseLong(capacity), model, manufacturer, year); //should calculate capapcity
            FlightDAO newFlightEntity = new FlightDAO(flightNumber, Double.parseDouble(price), origin,
                    destination, departureTime, arrivalTime, seatsLeft,
                    description, newPlaneEntity);
//            flightToBeUpdated.add(newFlightEntity);

            if(timesOverlap(previouslyBookedFlights, newFlightEntity))
             //   return new BadRequestDTO("404","Reservation failed because the flights' times overlap with each other or any other previously booked flight");
            {
//                System.out.println("hello overlapping of reservations timings for that particular flight:" + size);
                String msg = "the flight cannot be updated with the given arrival time, since the the times overlap with the other flights for the passenger" ;
                BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
                return badRequestDTO;
            }
            FlightDAO flightDAO = new FlightDAO(flightNumber, Double.parseDouble(price), origin, destination,
                    departureTime, arrivalTime, seatsLeft,
                    description, planeEntity);
            // This save method used here as update. Internally, it merges if the id exists.
            flightDAO = FlightRepository.save(flightDAO);

           // return BaseServiceImpl.mapFlightDAOToDTO(flightDAO);


         //   List<ReservationDAO> reservationEntities = flightEntity.getReservations();
            List<PassengerDAO> passengerEntities = new ArrayList<>();
            for (ReservationDAO reservationEntity : reservationEntities) {
                passengerEntities.add(reservationEntity.getPassenger());
            }
            List<PassengerDTO> passengerList = new ArrayList<>();

            for (PassengerDAO passengerEntity : passengerEntities) {
                passengerList.add(BaseServiceImpl.mapPassengerDAOtoDTO(passengerEntity));
            }

            PlaneDTO plane = new PlaneDTO();

            PlaneDTO plane1 = new PlaneDTO(flightDAO.getPlaneDAO().getCapacity().toString(),
                    flightDAO.getPlaneDAO().getModel(),
                    flightDAO.getPlaneDAO().getManufacturer(),
                    flightDAO.getPlaneDAO().getYear());
//        Passengers passengers = new Passengers(passengerList);
            FlightDTO flight = new FlightDTO(flightDAO.getFlightnumber(),
                    flightDAO.getPrice().toString(),
                    flightDAO.getOrigin(),
                    flightDAO.getDestination(),
                    flightDAO.getDeparturetime(),
                    flightDAO.getArrivaltime(),
                    flightDAO.getDescription(),
                    flightDAO.getSeatsleft().toString(),
                    plane1,
                    passengerList);

            return flight;
            //return null;*/
        }
        }
        else
            return null;
    }

    /**
     * Method to delete a file
     * @param flightNumber
     * @return
     */
    @Override
    public Object deleteFlight(String flightNumber)
    {
        FlightDAO FlightDAOToBeDeleted = FlightRepository.findByFlightnumber(flightNumber);


        if(FlightDAOToBeDeleted == null)
           // return new BadRequest("404", "flight with number "+flightNumber+" does not exist");
        {
            String msg = "the flight cannot be deleted, since the flight number" + flightNumber + "does not exist" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
            return badRequestDTO;
        }

        List<ReservationDAO> reservationEntities = FlightDAOToBeDeleted.getReservations();
        //Set<FlightDAO> flightEntitiesBookedByTheReservation = reservationEntities.getFlights();
        int size = reservationEntities.size();

        if(size > 0){
            System.out.println("the flight cannot be deleted, since the flight number" + flightNumber + "does not exist");
            String msg = "the flight cannot be deleted, since the flight number" + flightNumber + "does not exist" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
            return badRequestDTO;
        }
        else{

            FlightRepository.deleteById(flightNumber);
          //  return new ResponseDTO("200", "flight with number is canceled successfully");
            System.out.println("the flight number" + flightNumber + "is deleted");
            String msg = "the flight number" + flightNumber + "is deleted" ;
            ResponseDTO responseDTO = BaseController.formSuccessResponse("400",msg);
            return responseDTO;
        }
    }

    /**
     * Method to find a flight by ID
     * @param id the Flight's Id
     * @return
     */
    @Override
    public FlightDTO findFlightById(String id) {

        Optional<FlightDAO> flight = FlightRepository.findById(id);
        if(flight.isPresent()) {
            return BaseServiceImpl.mapFlightDAOToDTO(flight.get());
        } else {
            return null;
        }
    }
}

