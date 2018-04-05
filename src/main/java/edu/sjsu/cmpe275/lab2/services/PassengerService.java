package edu.sjsu.cmpe275.lab2.services;

import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;

public interface PassengerService {

	public PassengerDTO getPassenger(int id);

	public PassengerDTO createPassenger(String firstname, String lastname, int age,
										String gender, String phone);

	public PassengerDTO updatePassenger(int id, String firstname, String lastname, int age,
										String gender, String phone);

	public void deletePassenger(int id);
	
}
