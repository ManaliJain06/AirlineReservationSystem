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
                plane1,
                passengerList);

        return flight;
    }
    @Override
    public Object createFlight(String price, String origin, String destination, String departureTime, String arrivalTime, String description, String capacity,
                               String model, String manufacturer, String year) {
        String seatsLeft = capacity;
        PlaneDAO planeEntity = new PlaneDAO(Long.parseLong(capacity),model,manufacturer,year);
        FlightDAO flightEntity = new FlightDAO(Double.parseDouble(price), origin, destination, departureTime,arrivalTime, Long.valueOf(seatsLeft),
                description, planeEntity);
        flightEntity = FlightRepository.save(flightEntity);

        //ReservationServiceImpl.convertFlightEntityToDto(flightEntity);

        /*Plane plane1 = new Plane(flightEntity.getPlaneEntity().getCapacity().toString(),
                flightEntity.getPlaneEntity().getModel(),
                flightEntity.getPlaneEntity().getManufacturer(),
                flightEntity.getPlaneEntity().getYear().toString());
        List<Passenger> passengerList = new ArrayList<>();  //flight has just been created, hence the passengers list will be empty
        Passengers passengers = new Passengers(passengerList);
        Flight flight = new Flight(flightEntity.getFlightnumber().toString(),
                flightEntity.getPrice().toString(),
                flightEntity.getFrom(),
                flightEntity.getTo(),
                flightEntity.getDeparturetime(),
                flightEntity.getArrivaltime(),
                flightEntity.getDescription(),
                plane1,
                passengers);*/

        FlightDTO flight = BaseServiceImpl.mapFlightDAOToDTO(flightEntity);
        return flight;
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

