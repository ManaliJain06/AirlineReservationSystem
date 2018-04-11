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
@XmlType(propOrder = {"reservationNumber", "price", "passenger", "flights"})
public class ReservationDTO {
	
	private String reservationNumber;
	private String price;
	private PassengerDTO passenger;
	private Flights flights;
	
	public ReservationDTO() {}
	
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
	public PassengerDTO getPassenger() {
		return passenger;
	}
	public void setPassenger(PassengerDTO passenger) {
		this.passenger = passenger;
	}
	
	public Flights getFlights() {
		return flights;
	}
	public void setFlights(Flights flights) {
		this.flights = flights;
	}
}
