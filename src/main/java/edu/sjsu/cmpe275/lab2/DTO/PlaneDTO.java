package edu.sjsu.cmpe275.lab2.DTO;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "flight") 
@XmlType(propOrder = {"capacity", "model", "manufacturer", "year"})
public class PlaneDTO {
	
	private String capacity;
	private String model;
	private String manufacturer;
	private String year;

	public PlaneDTO(String capacity, String model, String manufacturer, String year) {
		super();
		this.capacity = capacity;
		this.model = model;
		this.manufacturer = manufacturer;
		this.year = year;
	}
	public PlaneDTO() {}

	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	};
}
