package edu.sjsu.cmpe275.lab2.DTO;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("passenger")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@XmlRootElement(name = "flight") 
@XmlType(propOrder = {"flightNumber", "price", "from", "to", "departureTime", "arrivalTime", "description","seatsLeft", "planeDTO", "passengerDTOS"})
public class FlightDTO {
	
	private String flightNumber;
	private String price;
	private String from;
	private String to;
	private String departureTime;
	private String arrivalTime;
	private String description;
	private String seatsLeft;
	private PlaneDTO plane;
	private List<PassengerDTO> passengers;

	public FlightDTO() {}

	public FlightDTO(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime,
					 String description, PlaneDTO plane, List<PassengerDTO> passengers) {
		super();
		this.flightNumber = flightNumber;
		this.price = price;
		this.from = from;
		this.to = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.description = description;
		this.plane = plane;
		this.passengers = passengers;
	}
	public FlightDTO(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime,
				  String seatsLeft, String description, PlaneDTO plane) {
		this.flightNumber = flightNumber;
		this.price = price;
		this.from = from;
		this.to = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsLeft = seatsLeft;
		this.description = description;
		this.plane = plane;
	}

//	public FlightDTO(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime,
//				  String description, PlaneDTO plane) {
//		super();
//		this.flightNumber = flightNumber;
//		this.price = price;
//		this.from = from;
//		this.to = to;
//		this.departureTime = departureTime;
//		this.arrivalTime = arrivalTime;
//		this.description = description;
//		this.plane = plane;
//	}


	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String number) {
		this.flightNumber = number;
	}

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public PlaneDTO getPlane() {
		return plane;
	}

	public void setPlane(PlaneDTO plane) {
		this.plane = plane;
	}

	@JsonInclude(Include.NON_NULL)
	public List<PassengerDTO> getPassengerDTOS() {
		return passengers;
	}

	public void setPassenger(List<PassengerDTO> passengers) {
		this.passengers = passengers;
	};

	public String getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(String seatsLeft) {
		this.seatsLeft = seatsLeft;
	}


}
