package edu.sjsu.cmpe275.lab2.DTO;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reservations")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
public class Reservations {
	
	private List<ReservationDTO> reservation;

	public Reservations() {};

	public Reservations(List<ReservationDTO> reservation) {
		super();
		this.reservation = reservation;
	}
	public List<ReservationDTO> getReservation() {
		return reservation;
	}
	public void setReservation(List<ReservationDTO> reservation) {
		this.reservation = reservation;
	}

}
