package edu.sjsu.cmpe275.lab2.DAO;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="reservation")
public class ReservationDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_number")
    private Integer reservationnumber;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    private PassengerDAO passenger;

    @ManyToMany
    @JoinTable
            (
            name="flight_reservations", joinColumns = {@JoinColumn(name = "reservation_number", referencedColumnName = "reservation_number")},
            inverseJoinColumns = {@JoinColumn(name = "flight_number", referencedColumnName = "flight_number")}
    )
    private List<FlightDAO> flights;
    private Double price;

    public ReservationDAO(){}

    public ReservationDAO(PassengerDAO passenger,Double price, List<FlightDAO> flights) {
        this.passenger = passenger;
        this.price = price;
        this.flights = flights;
    }

    public PassengerDAO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerDAO passenger) {
        this.passenger = passenger;
    }

    public void setReservationnumber(Integer reservationnumber)
    {
        this.reservationnumber = reservationnumber;
    }

    public Integer getReservationnumber()
    {
        return reservationnumber;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }

    public List<FlightDAO> getFights() {
        return flights;
    }

    public void setFlights(List<FlightDAO> flights) {
        this.flights = flights;
    }
}

