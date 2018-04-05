package edu.sjsu.cmpe275.lab2.DTO;

import java.util.List;

public class Flights {
	
	private List<FlightDTO> flightDTO;
	
	public Flights() {};
	
	public Flights(List<FlightDTO> flightDTO) {
		this.flightDTO = flightDTO;
	}
	
	public List<FlightDTO> getFlightDTO() {
		return flightDTO;
	}

	public void setFlightDTO(List<FlightDTO> flightDTO) {
		this.flightDTO = flightDTO;
	}

}
