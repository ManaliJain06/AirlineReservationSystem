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

@Service
public class FlightServiceImpl implements FlightService{
    @Autowired
    private FlightRepository FlightRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * This method is used to fetch the information about Passenger and his/her
     * reservation details.
     *
     * @param id
     *            the Flight's Id
     * @return the Flight dto
     */
    @Override
    public Object getFlight(String id) {

        FlightDAO flightEntity = FlightRepository.findByFlightnumber(Long.parseLong(id));
        PlaneDAO planeEntity;
        if(flightEntity == null)
        {
            String msg = "Flight with number " + id + " does not exist";
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",msg);
            return badRequestDTO;
        }
        //list of reservations
        //list of passengers(input reservations as parameter

        List<ReservationDAO> reservationEntities = flightEntity.getReservations();
        List<PassengerDAO> passengerEntities = new ArrayList<>();
        for(ReservationDAO reservationEntity: reservationEntities)
        {
            passengerEntities.add(reservationEntity.getPassenger());
        }

        List<PassengerDTO> passengerList = new ArrayList<>();

        for(PassengerDAO passengerEntity: passengerEntities)
        {
            passengerList.add(BaseServiceImpl.mapPassengerDAOtoDTO(passengerEntity));
        }

        PlaneDTO plane = new PlaneDTO();

        PlaneDTO plane1 = new PlaneDTO(flightEntity.getPlaneEntity().getCapacity().toString(),
                flightEntity.getPlaneEntity().getModel(),
                flightEntity.getPlaneEntity().getManufacturer(),
                flightEntity.getPlaneEntity().getYear().toString());
//        Passengers passengers = new Passengers(passengerList);
        FlightDTO flight = new FlightDTO(flightEntity.getFlightnumber().toString(),
                flightEntity.getPrice().toString(),
                flightEntity.getOrigin(),
                flightEntity.getDestination(),
                flightEntity.getDeparturetime(),
                flightEntity.getArrivaltime(),
                flightEntity.getDescription(),
                flightEntity.getSeatsleft().toString(),
                plane1,
                passengerList);

        return flight;
    }
    @Override
    public Object createFlight(String flightNumber, String price, String origin, String destination,
                               String departureTime, String arrivalTime, String description, String capacity,
                               String model, String manufacturer, String year) {
        String seatsLeft = capacity;
        PlaneDAO planeEntity = new PlaneDAO(Long.parseLong(capacity),model,manufacturer,year);
            FlightDAO flightEntity = new FlightDAO(Long.valueOf(flightNumber),Double.parseDouble(price), origin, destination,
                    departureTime,arrivalTime, Long.valueOf(seatsLeft),
                    description, planeEntity);
            flightEntity = FlightRepository.save(flightEntity);

        //ReservationServiceImpl.convertFlightEntityToDto(flightEntity);

        PlaneDTO plane1 = new PlaneDTO(flightEntity.getPlaneEntity().getCapacity().toString(),
                flightEntity.getPlaneEntity().getModel(),
                flightEntity.getPlaneEntity().getManufacturer(),
                flightEntity.getPlaneEntity().getYear().toString());
        List<PassengerDTO> passengerList = new ArrayList<>();  //flight has just been created, hence the passengers list will be empty
        Passengers passengers = new Passengers(passengerList);
        FlightDTO flight = new FlightDTO(flightEntity.getFlightnumber().toString(),
                flightEntity.getPrice().toString(),
                flightEntity.getOrigin(),
                flightEntity.getDestination(),
                flightEntity.getDeparturetime(),
                flightEntity.getArrivaltime(),
                flightEntity.getDescription(),
                flightEntity.getSeatsleft().toString(),
                plane1,
                passengerList);

     //   FlightDTO flight = BaseServiceImpl.mapFlightDAOToDTO(flightEntity);
        return flight;
    }

    @Override
    public Object updateFlight(String flightNumber,String price, String origin, String destination, String departureTime, String arrivalTime, String description,
                               String capacity,
                               String model, String manufacturer, String year) {

       // long passengerId = Long.parseLong(id);
      //   long Capacity ;
        FlightDAO flightEntity = FlightRepository.findByFlightnumber(Long.parseLong(flightNumber));
        List<ReservationDAO> reservationEntities = flightEntity.getReservations();
        // update the details.
       // long seatsLeft ;

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
            long Capacity = flightEntity.getPlaneEntity().getCapacity(); //capacity there in the particular flight before update
            long seatsLeft =flightEntity.getSeatsleft();
            long capacityDiff = Long.parseLong(capacity)-Capacity;         //   long SeatsLeft = Long.valueOf(seatsLeft);
               if(seatsLeft!=0 && capacityDiff > 0 ) //if seatsLeft is zero and difference is negative, the seatsLEft will be updated to negative, hence exception will be thrown
               {
                   seatsLeft = Long.valueOf(seatsLeft) + capacityDiff; //i update capacity to 10 from 5, seatsLeft is changed from 5 to 0
               }

            FlightDAO flightDAO = new FlightDAO(Long.valueOf(flightNumber), Double.parseDouble(price), origin, destination,
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

            PlaneDTO plane1 = new PlaneDTO(flightDAO.getPlaneEntity().getCapacity().toString(),
                    flightDAO.getPlaneEntity().getModel(),
                    flightDAO.getPlaneEntity().getManufacturer(),
                    flightDAO.getPlaneEntity().getYear().toString());
//        Passengers passengers = new Passengers(passengerList);
            FlightDTO flight = new FlightDTO(flightDAO.getFlightnumber().toString(),
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

    @Override
    public FlightDTO findFlightById(String id) {

        Optional<FlightDAO> entity = FlightRepository.findById(Long.parseLong(id));
        if(entity.isPresent()) {
            return BaseServiceImpl.mapFlightDAOToDTO(entity.get());
        } else {
            return null;
        }
    }



}

