package edu.sjsu.cmpe275.lab2.DTO;

import java.util.List;

public class Flights {

	private List<FlightDTO> flight;

	public Flights() {};

	public Flights(List<FlightDTO> flight) {
		this.flight = flight;
	}

	public List<FlightDTO> getFlight() {
		return flight;
	}

	public void setFlight(List<FlightDTO> flight) {
		this.flight = flight;
	}

}
