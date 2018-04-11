package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
/**
 * Interface for Passenger
 * Author: Manali Jain
 */
public interface PassengerService {

	/**
	 * Interface to get the passenger details
	 * @param id
	 * @return PassengerDTO
	 */
	PassengerDTO getPassenger(int id);

	/**
	 * Interface to create a passenger
	 * @param firstname
	 * @param lastname
	 * @param age
	 * @param gender
	 * @param phone
	 * @return PassengerDTO
	 */
	PassengerDTO createPassenger(String firstname, String lastname, int age,
										String gender, String phone);

	/**
	 * Interface to update a passenger
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param age
	 * @param gender
	 * @param phone
	 * @return PassengerDTO
	 */
	PassengerDTO updatePassenger(int id, String firstname, String lastname, int age,
										String gender, String phone);

	/**
	 * Interface to delete a passenger
	 * @param id
	 */
	void deletePassenger(int id);
	
}
