package edu.sjsu.cmpe275.lab2.DTO;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("flight")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@XmlRootElement(name = "flight")
@XmlType(propOrder = {"flightNumber", "price", "origin", "destination", "departureTime", "arrivalTime", "description","seatsLeft", "plane", "passengers"})
public class FlightDTO {

	private String flightNumber;
	private String price;
	private String origin;
	private String destination;
	private String departureTime;
	private String arrivalTime;
	private String description;
	private String seatsLeft;
	private PlaneDTO plane;
	private List<PassengerDTO> passengers;

	public FlightDTO() {}

	public FlightDTO(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime,
					 String description, String seatsLeft,PlaneDTO plane, List<PassengerDTO> passengers) {
		super();
		this.flightNumber = flightNumber;
		this.price = price;
		this.origin = from;
		this.destination = to;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.description = description;
		this.seatsLeft = seatsLeft;
		this.plane = plane;
		this.passengers = passengers;
	}
	public FlightDTO(String flightNumber, String price, String from, String to, String departureTime, String arrivalTime,
				  String seatsLeft, String description, PlaneDTO plane) {
		this.flightNumber = flightNumber;
		this.price = price;
		this.origin = from;
		this.destination = to;
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

	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String from) {
		this.origin = from;
	}

	public String getDestination() {
		return destination;
	}
	public void setDestination(String to) {
		this.destination = to;
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
	public List<PassengerDTO> getPassengers() {
		return passengers;
	}
	@XmlElement
	public void setPassengers(List<PassengerDTO> passengers) {
		this.passengers = passengers;
	};

	public String getSeatsLeft() {
		return seatsLeft;
	}

	public void setSeatsLeft(String seatsLeft) {
		this.seatsLeft = seatsLeft;
	}


}
