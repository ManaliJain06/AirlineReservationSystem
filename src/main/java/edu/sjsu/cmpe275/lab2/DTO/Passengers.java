package edu.sjsu.cmpe275.lab2.DTO;

import java.util.List;

public class Passengers {
	
	private List<PassengerDTO> passengerDTO;

	public Passengers() {};
	
	public Passengers(List<PassengerDTO> passengerDTO) {
		super();
		this.passengerDTO = passengerDTO;
	}
	public List<PassengerDTO> getPassengerDTO() {
		return passengerDTO;
	}
	public void setPassengerDTO(List<PassengerDTO> passengerDTO) {
		this.passengerDTO = passengerDTO;
	}

}
