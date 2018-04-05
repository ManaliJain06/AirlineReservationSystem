package edu.sjsu.cmpe275.lab2.serviceImpl;

import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;
import edu.sjsu.cmpe275.lab2.DTO.FlightDTO;
import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.DTO.PlaneDTO;

public class BaseServiceImpl {

    public static PassengerDTO mapPassengerDAOtoDTO(PassengerDAO passengerDao){
        PassengerDTO passenger = new PassengerDTO();
        passenger.setId(Integer.toString(passengerDao.getId()));
        passenger.setFirstname(passengerDao.getFirstname());
        passenger.setLastname(passengerDao.getLastname());
        passenger.setAge(Integer.toString(passengerDao.getAge()));
        passenger.setGender(passengerDao.getGender());
        passenger.setPhone(passengerDao.getPhone());
        passenger.setReservations(null);
        return passenger;
    }

    public static FlightDTO mapFlightDAOToDTO(FlightDAO flightdao)
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
    }
}
