package edu.sjsu.cmpe275.lab2.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonTypeName("reservation")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@XmlRootElement(name = "reservation") 
@XmlType(propOrder = {"reservationNumber", "price", "passengerDTO", "flights"})
public class ReservationDTO {
	
	private String reservationNumber;
	private String price;
	private PassengerDTO passengerDTO;
	private Flights flights;
	
	public ReservationDTO() {};
	
	public ReservationDTO(String reservationNumber, String price, PassengerDTO passengerDTO, Flights flights) {
		super();
		this.reservationNumber = reservationNumber;
		this.price = price;
		this.passengerDTO = passengerDTO;
		this.flights = flights;
	}
	
	public String getReservationNumber() {
		return reservationNumber;
	}
	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	@JsonInclude(Include.NON_NULL)
	public PassengerDTO getPassengerDTO() {
		return passengerDTO;
	}
	public void setPassengerDTO(PassengerDTO passengerDTO) {
		this.passengerDTO = passengerDTO;
	}
	
	public Flights getFlights() {
		return flights;
	}
	public void setFlights(Flights flights) {
		this.flights = flights;
	}
}
