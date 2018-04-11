package edu.sjsu.cmpe275.lab2.DAO;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "flight")
public class FlightDAO {

    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="flight_number")
    private String flightnumber;

    private Double price;
    private String origin;
    private String destination;

    @Column(name="departure_time")
    private String departuretime;

    @Column(name="arrival_time")
    private String arrivaltime;

    @Column(name="seats_left")
    private Long seatsleft;
    private String description;

    @Embedded
    private PlaneDAO planeDAO;

    @ManyToMany(mappedBy = "flights")
    private List<ReservationDAO> reservations;


    public FlightDAO() {};

    public String getFlightnumber() {
        return flightnumber;
    }

    public List<ReservationDAO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDAO> reservations) {
        this.reservations = reservations;
    }

    public FlightDAO(String flightnumber, Double price, String origin, String destination, String departuretime,
                     String arrivaltime, Long seatsleft, String description, PlaneDAO planeDAO,
                     List<ReservationDAO> reservations) {
        this.flightnumber = flightnumber;
        this.price = price;
        this.origin = origin;
        this.destination = destination;
        this.departuretime = departuretime;
        this.arrivaltime = arrivaltime;
        this.seatsleft = seatsleft;
        this.description = description;
        this.planeDAO = planeDAO;

        this.reservations = reservations;
    }

    public FlightDAO(String flightnumber,Double price, String origin, String destination, String departuretime,
                     String arrivaltime, Long seatsleft, String description, PlaneDAO planeDAO) {
        this.flightnumber = flightnumber;
        this.price = price;
        this.origin = origin;
        this.destination = destination;  //changed by hanisha
        this.departuretime = departuretime;
        this.arrivaltime = arrivaltime;
        this.seatsleft = seatsleft;
        this.description = description;
        this.planeDAO = planeDAO;
    }

    public void setFlightnumber(String flightnumber) {

        this.flightnumber = flightnumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(String departuretime) {
        this.departuretime = departuretime;
    }

    public String getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(String arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public Long getSeatsleft() {
        return seatsleft;
    }

    public void setSeatsleft(Long seatsleft) {
        this.seatsleft = seatsleft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlaneDAO getPlaneDAO() {
        return planeDAO;
    }

    public void setPlaneDAO(PlaneDAO planeDAO) {
        this.planeDAO = planeDAO;
    }


}
