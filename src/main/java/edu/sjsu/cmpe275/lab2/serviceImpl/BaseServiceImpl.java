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

/**
 * Base Util class to write mapper codes for connecting DAO to DTO
 */
public class BaseServiceImpl {

    /**
     * Mapper for mapping Passenger DAO to DTO
     * @param passengerDao
     * @return PassengerDTO
     */
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

    /**
     * Mapper for mapping Flight DAO to DTO
     * @param flightdao
     * @return FlightDTO
     */
    public static FlightDTO mapFlightDAOToDTO(final FlightDAO flightdao)
    {
        PlaneDTO plane = new PlaneDTO(flightdao.getPlaneDAO().getCapacity().toString(), flightdao.getPlaneDAO().getModel(),
                flightdao.getPlaneDAO().getManufacturer(), flightdao.getPlaneDAO().getYear());
        FlightDTO flight = new FlightDTO(flightdao.getFlightnumber(), flightdao.getPrice().toString(),
                flightdao.getOrigin(), flightdao.getDestination(), flightdao.getDeparturetime(), flightdao.getArrivaltime(),
                flightdao.getSeatsleft().toString(), flightdao.getDescription(), plane);

        return flight;
    }

    /**
     * Mapper for mapping Reservation DAO to DTO
     * @param reservationDAO
     * @param totalPrice
     * @return ReservationDTO
     */
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

    /**
     * Mapper for mapping Reservation DAO to DTO for passenger
     * @param reservationDAO
     * @return
     */
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
