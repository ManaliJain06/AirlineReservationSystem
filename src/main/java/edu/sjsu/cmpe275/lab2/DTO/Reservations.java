package edu.sjsu.cmpe275.lab2.DTO;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Reservations {
	
	private List<ReservationDTO> reservationDTO;
	
	public Reservations() {};
	
	public Reservations(List<ReservationDTO> reservationDTO) {
		super();
		this.reservationDTO = reservationDTO;
	}
	public List<ReservationDTO> getReservationDTO() {
		return reservationDTO;
	}
	@XmlElement
	public void setReservationDTO(List<ReservationDTO> reservationDTO) {
		this.reservationDTO = reservationDTO;
	}

}
