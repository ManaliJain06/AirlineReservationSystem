package edu.sjsu.cmpe275.lab2.serviceImpl;

import edu.sjsu.cmpe275.lab2.DAO.FlightDAO;
import edu.sjsu.cmpe275.lab2.DAO.ReservationDAO;
import edu.sjsu.cmpe275.lab2.repository.FlightRepository;
import edu.sjsu.cmpe275.lab2.repository.PassengerRepository;
import edu.sjsu.cmpe275.lab2.repository.ReservationRepository;
import edu.sjsu.cmpe275.lab2.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;

import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements all the operations of the Passenger
 * Author: Manali Jain
 */

@Service
public class PassengerServiceImpl implements PassengerService {

	private PassengerRepository passengerRepository;
	private FlightRepository flightRepository;
	private ReservationRepository reservationRepository;

	@Autowired
	PassengerServiceImpl(PassengerRepository passengerRepository,
						 FlightRepository flightRepository, ReservationRepository reservationRepository){

		this.passengerRepository = passengerRepository;
		this.flightRepository = flightRepository;
		this.reservationRepository = reservationRepository;
	}

	/**
	 * Method to get the passenger details
	 * @param id
	 * @return PassengerDTO object
	 */
	@Override
	public PassengerDTO getPassenger(int id) {
		PassengerDAO passengerDao = passengerRepository.getById(id);
		if(passengerDao != null){
			 return BaseServiceImpl.mapPassengerDAOtoDTO(passengerDao);
		} else{
			return null;
		}
	}

	/**
	 * Method to create a passenger
	 * @param firstname
	 * @param lastname
	 * @param age
	 * @param gender
	 * @param phone
	 * @return PassengerDTO object
	 */
	@Override
	public PassengerDTO createPassenger(String firstname, String lastname, int age,
										String gender, String phone){

		PassengerDAO passengerDAO = passengerRepository.getByPhone(phone);
		if(passengerDAO == null){
			PassengerDAO newPassenger = new PassengerDAO(firstname, lastname, age, gender, phone);
			PassengerDAO recentlySavedPassenger = passengerRepository.save(newPassenger);

			return BaseServiceImpl.mapPassengerDAOtoDTO(recentlySavedPassenger);
		} else{
			return null;
		}
	}

	/**
	 * Method to update a passenger
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param age
	 * @param gender
	 * @param phone
	 * @return PassengerDTO object
	 */
	@Override
	public PassengerDTO updatePassenger(int id, String firstname, String lastname, int age,
											 String gender, String phone){

		PassengerDTO updatedPassengerDAO;
		PassengerDAO passengerToUpdate = new PassengerDAO(id, firstname, lastname, age, gender, phone);
		PassengerDAO pass = passengerRepository.getByPhone(phone);
		if(pass!= null) {
			if(pass.getId() == id) {
				//case when the user is not Updating his number but updating other values
				updatedPassengerDAO = saveExistingPassenger(passengerToUpdate);
				return updatedPassengerDAO;
			} else {
				//case when the user is number is already in the database with some other passenger id
				return null;
			}
		} else {
			//case when the user is updating his number
			updatedPassengerDAO = saveExistingPassenger(passengerToUpdate);
			return updatedPassengerDAO;
		}
	}

	/**
	 * Method to save an existing passenger
	 * @param passengerToUpdate
	 * @return PassengerDTO
	 */
	private PassengerDTO saveExistingPassenger(PassengerDAO passengerToUpdate){
		PassengerDAO updatedPassengerDAO = passengerRepository.save(passengerToUpdate);
		return BaseServiceImpl.mapPassengerDAOtoDTO(updatedPassengerDAO);

	}

	/**
	 * Method to delete a passenger
	 * @param id
	 */
	@Override
	public void deletePassenger(int id){
		PassengerDAO passenger = passengerRepository.getById(id);
		List<ReservationDAO> reservationsOfPassenger =  passenger.getReservationsOfPassengers();

		if(reservationsOfPassenger.size()>0){
			// get all the flights booked of a user
			List<FlightDAO>  allFlights  = new ArrayList<>();
			for(ReservationDAO reservation: reservationsOfPassenger){
				List<FlightDAO> flightsBooked = reservation.getFights();
				allFlights.addAll(flightsBooked);

				//delete the reservation then
				int deleted = reservationRepository.deleteByReservationnumber(
						reservation.getReservationnumber());
			}

			// update all the flights booked for a user
			for(FlightDAO flightsToBeUpdate: allFlights){
				Long seatsLeft = flightsToBeUpdate.getSeatsleft();
				flightsToBeUpdate.setSeatsleft(seatsLeft -1);
				flightRepository.save(flightsToBeUpdate);
			}

			// delete the passenger
			passengerRepository.deleteById(id);
		} else{
			// delete the passenger
			passengerRepository.deleteById(id);
		}
	}
}
