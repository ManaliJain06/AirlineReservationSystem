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
   /*
   When flights are updated, this function checks if the current flight being updated and the flights already reserved are overlapping for a passenger
    */
    private Boolean timesOverlap(Set<FlightDAO> previouslyBookedFlights, Set<FlightDAO> flightToBeUpdated)
    {
        Date min = new Date();
        Date max = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH");

        for(FlightDAO flightEntity: flightToBeUpdated)
        {
            try {
                Date departureTime = dateFormat.parse(flightEntity.getDeparturetime());
                Date arrivalTime = dateFormat.parse(flightEntity.getArrivaltime());

                for(FlightDAO flight: previouslyBookedFlights)
                {
                    min = dateFormat.parse(flight.getDeparturetime());
                    max = dateFormat.parse(flight.getArrivaltime());
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(max);
                    calendar.add(Calendar.HOUR, 1);
                    max = calendar.getTime();

                    if((departureTime.after(min) && departureTime.before(max)) ||
                            (arrivalTime.after(min) && arrivalTime.before(max)))
                        return true;
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return false;
    }

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

        FlightDAO flightEntity = FlightRepository.findByFlightnumber(id);
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
            FlightDAO flightEntity = new FlightDAO(flightNumber,Double.parseDouble(price), origin, destination,
                    departureTime,arrivalTime, Long.valueOf(seatsLeft),
                    description, planeEntity);
            flightEntity = FlightRepository.save(flightEntity);

        //ReservationServiceImpl.convertFlightEntityToDto(flightEntity);
        if(flightEntity != null) {
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
        else{
            System.out.println("hello creation of the flight could not be successful:" );
            String msg = "creation of the flight could not be successful" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",msg);
            return badRequestDTO;
        }
    }

    @Override
    public Object updateFlight(String flightNumber,String price, String origin, String destination, String departureTime, String arrivalTime, String description,
                               String capacity,
                               String model, String manufacturer, String year) {

       // long passengerId = Long.parseLong(id);
      //   long Capacity ;
        FlightDAO flightEntity = FlightRepository.findByFlightnumber(flightNumber);
        Set<FlightDAO> flightToBeUpdated = new HashSet<>();
        flightToBeUpdated.add(flightEntity);
        List<ReservationDAO> reservationEntities = flightEntity.getReservations();
        // update the details.
       // long seatsLeft ;
        Set<FlightDAO> previouslyBookedFlights = new HashSet<>();
        for (ReservationDAO reservationEntity : reservationEntities) {
            List<FlightDAO> currentReservedFlights=reservationEntity.getFlights();
            for(FlightDAO flight: currentReservedFlights)
                previouslyBookedFlights.add(flight);
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
            long Capacity = flightEntity.getPlaneEntity().getCapacity(); //capacity there in the particular flight before update
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

            if(timesOverlap(previouslyBookedFlights, flightToBeUpdated))
             //   return new BadRequestDTO("404","Reservation failed because the flights' times overlap with each other or any other previously booked flight");
            {
                System.out.println("hello overlapping of reservations timings for that particular flight:" + size);
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
    public Object deleteFlight(String flightNumber)
    {
        FlightDAO FlightEntityToBeDeleted = FlightRepository.findByFlightnumber(flightNumber);


        if(FlightEntityToBeDeleted == null)
           // return new BadRequest("404", "flight with number "+flightNumber+" does not exist");
        {
            String msg = "the flight cannot be deleted, since the flight number" + flightNumber + "does not exist" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
            return badRequestDTO;
        }

        List<ReservationDAO> reservationEntities = FlightEntityToBeDeleted.getReservations();
        //Set<FlightDAO> flightEntitiesBookedByTheReservation = reservationEntities.getFlights();
        int size = reservationEntities.size();

        if(size > 0){
            System.out.println("the flight cannot be deleted, since the flight number" + flightNumber + "does not exist");
            String msg = "the flight cannot be deleted, since the flight number" + flightNumber + "does not exist" ;
            BadRequestDTO badRequestDTO = BaseController.formBadRequest("400",msg);
            return badRequestDTO;
        }
        else{

           /* FlightEntityToBeDeleted =*/ FlightRepository.deleteById(flightNumber);
          //  return new ResponseDTO("200", "flight with number is canceled successfully");
            System.out.println("the flight number" + flightNumber + "is deleted");
            String msg = "the flight number" + flightNumber + "is deleted" ;
            ResponseDTO responseDTO = BaseController.formSuccessResponse("400",msg);
            return responseDTO;
        }
    }

    @Override
    public FlightDTO findFlightById(String id) {

        Optional<FlightDAO> entity = FlightRepository.findById(id);
        if(entity.isPresent()) {
            return BaseServiceImpl.mapFlightDAOToDTO(entity.get());
        } else {
            return null;
        }
    }



}

