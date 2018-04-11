package edu.sjsu.cmpe275.lab2.DAO;

import javax.persistence.Embeddable;

@Embeddable
public class PlaneDAO {

    private Long capacity;
    private String model;
    private String manufacturer;
    private String year;

    public PlaneDAO(){}

    public PlaneDAO(Long capacity, String model, String manufacturer, String year) {
        this.capacity = capacity;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
    }
    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
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
    }
}
