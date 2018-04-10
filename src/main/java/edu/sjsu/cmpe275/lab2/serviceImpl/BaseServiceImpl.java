package edu.sjsu.cmpe275.lab2.serviceImpl;

import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;
import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.DTO.FlightDTO;
import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.DTO.PlaneDTO;
import edu.sjsu.cmpe275.lab2.DTO.ReservationDTO;
import edu.sjsu.cmpe275.lab2.DTO.Flights;
import edu.sjsu.cmpe275.lab2.DTO.Reservations;

import java.util.ArrayList;
import java.util.List;

public class BaseServiceImpl {

    public static PassengerDTO mapPassengerDAOtoDTO(final PassengerDAO passengerDao){
        PassengerDTO passenger = new PassengerDTO();
        passenger.setId(Integer.toString(passengerDao.getId()));
        passenger.setFirstname(passengerDao.getFirstname());
        passenger.setLastname(passengerDao.getLastname());
        passenger.setAge(Integer.toString(passengerDao.getAge()));
        passenger.setGender(passengerDao.getGender());
        passenger.setPhone(passengerDao.getPhone());

        List<ReservationDTO> allReservation = new ArrayList<>();
        if(passengerDao.getReservationsOfPassengers() != null){
            for(ReservationDAO reservation : passengerDao.getReservationsOfPassengers()){
                ReservationDTO res = mapReservationDAOToDTOForPassenger(reservation);
                allReservation.add(res);
            }
        }
        Reservations reservations = new Reservations(allReservation);
        passenger.setReservations(reservations);
        return passenger;
    }

    public static FlightDTO mapFlightDAOToDTO(final FlightDAO flightdao)
    {
        PlaneDTO plane = new PlaneDTO(flightdao.getPlaneEntity().getCapacity().toString(),
                flightdao.getPlaneEntity().getModel(),
                flightdao.getPlaneEntity().getManufacturer(),
                flightdao.getPlaneEntity().getYear().toString());
        FlightDTO flight = new FlightDTO(flightdao.getFlightnumber().toString(),
                flightdao.getPrice().toString(),
                flightdao.getOrigin(),
                flightdao.getDestination(),
                flightdao.getDeparturetime(),
                flightdao.getArrivaltime(),
                flightdao.getSeatsleft().toString(),
                flightdao.getDescription(),
                plane);

        return flight;


//        Plane plane = new Plane(flightEntity.getPlaneEntity().getCapacity().toString(),
//                flightEntity.getPlaneEntity().getModel(),
//                flightEntity.getPlaneEntity().getManufacturer(),
//                flightEntity.getPlaneEntity().getYear().toString());
//
//        Flight flight = new Flight(flightEntity.getFlightnumber().toString(),
//                flightEntity.getPrice().toString(),
//                flightEntity.getOrigin(),
//                flightEntity.getDestination(),
//                flightEntity.getDeparturetime(),
//                flightEntity.getArrivaltime(),
//                flightEntity.getSeatsleft().toString(),
//                flightEntity.getDescription(),
//                plane,
//                passengers);

//        return flight;
    }

    public static ReservationDTO mapReservationDAOToDTO(final ReservationDAO reservationDAO, final Double totalPrice){

        PassengerDAO passengerDao = reservationDAO.getPassenger();
        PassengerDTO passenger = new PassengerDTO();
        passenger.setId(Integer.toString(passengerDao.getId()));
        passenger.setFirstname(passengerDao.getFirstname());
        passenger.setLastname(passengerDao.getLastname());
        passenger.setAge(Integer.toString(passengerDao.getAge()));
        passenger.setGender(passengerDao.getGender());
        passenger.setPhone(passengerDao.getPhone());

        List<FlightDTO> allFlights = new ArrayList<>();
        for(FlightDAO flightsBooked: reservationDAO.getFights()){
            FlightDTO flight = mapFlightDAOToDTO(flightsBooked);
            allFlights.add(flight);
        }

        Flights flights = new Flights(allFlights);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationNumber(String.valueOf(reservationDAO.getReservationnumber()));
        reservationDTO.setPrice(String.valueOf(totalPrice));
        reservationDTO.setPassenger(passenger);
        reservationDTO.setFlights(flights);

        return reservationDTO;
    }

    public static ReservationDTO mapReservationDAOToDTOForPassenger(final ReservationDAO reservationDAO){


        List<FlightDTO> allFlights = new ArrayList<>();
        for(FlightDAO flightsBooked: reservationDAO.getFights()){
            FlightDTO flight = mapFlightDAOToDTO(flightsBooked);
            allFlights.add(flight);
        }

        Flights flights = new Flights(allFlights);

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationNumber(String.valueOf(reservationDAO.getReservationnumber()));
        reservationDTO.setPrice(String.valueOf(reservationDAO.getPrice()));
        reservationDTO.setFlights(flights);

        return reservationDTO;
    }
}
