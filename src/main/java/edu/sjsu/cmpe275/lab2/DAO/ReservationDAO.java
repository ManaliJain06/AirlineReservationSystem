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

    private Double price;
    //private Integer passengerid;
/*

    @ManyToMany(mappedBy = "reservations")
    private List<FlightEntity> flights;
*/

    public ReservationDAO(Double price, PassengerDAO passenger) {
        this.price = price;
        this.passenger = passenger;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    private PassengerDAO passenger;

    @ManyToMany
    @JoinTable(
            name="flight_reservations",
            joinColumns = @JoinColumn(name = "reservation_number", referencedColumnName = "reservation_number"),
            inverseJoinColumns = @JoinColumn(name = "flight_number", referencedColumnName = "flight_number")
    )
    private List<FlightDAO> flights;

    public ReservationDAO(Double price, PassengerDAO passenger, List<FlightDAO> flights) {
        this.price = price;
        this.passenger = passenger;
        this.flights = flights;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    public List<FlightDAO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightDAO> flights) {
        this.flights = flights;
    }

    public ReservationDAO(){}

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

}

