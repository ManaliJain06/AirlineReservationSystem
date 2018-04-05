package edu.sjsu.cmpe275.lab2.serviceImpl;

import edu.sjsu.cmpe275.lab2.repository.PassengerRepository;
import edu.sjsu.cmpe275.lab2.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.sjsu.cmpe275.lab2.DAO.PassengerDAO;

import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;

/**
 * This class implements all the operations of the Passenger
 * Author: Manali Jain
 */

@Service
public class PassengerServiceImpl implements PassengerService {

	private PassengerRepository passengerRepository;
	@Autowired

	PassengerServiceImpl(PassengerRepository passengerRepository){
		this.passengerRepository = passengerRepository;
	}

	@Override
	public PassengerDTO getPassenger(int id) {
		PassengerDAO passengerDao = passengerRepository.getById(id);
		if(passengerDao != null){
			 return BaseServiceImpl.mapPassengerDAOtoDTO(passengerDao);
		} else{
			return null;
		}
	}

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

	private PassengerDTO saveExistingPassenger(PassengerDAO passengerToUpdate){
		PassengerDAO updatedPassengerDAO = passengerRepository.save(passengerToUpdate);

		PassengerDTO updatedPassenger = new PassengerDTO();
		updatedPassenger.setId(Integer.toString(updatedPassengerDAO.getId()));
		updatedPassenger.setFirstname(updatedPassengerDAO.getFirstname());
		updatedPassenger.setLastname(updatedPassengerDAO.getLastname());
		updatedPassenger.setAge(Integer.toString(updatedPassengerDAO.getAge()));
		updatedPassenger.setGender(updatedPassengerDAO.getGender());
		updatedPassenger.setPhone(updatedPassengerDAO.getPhone());
		updatedPassenger.setReservations(null);
		return updatedPassenger;
	}

	@Override
	public void deletePassenger(int id){
		passengerRepository.deleteById(id);
	}

	
}
