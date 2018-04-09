package edu.sjsu.cmpe275.lab2.DAO;

import javax.persistence.*;
import java.util.List;
@Entity(name = "passenger")
public class PassengerDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="first_name")
    private String firstname;

    @Column(name="last_name")
    private String lastname;

    private int age;

    private String gender;

    @Column(unique=true)
    private String phone;

    @OneToMany(mappedBy = "passenger")
    private List<ReservationDAO> reservationsOfPassengers;

    public PassengerDAO(){};

    public PassengerDAO(String firstname, String lastname, int age, String gender, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }
    public PassengerDAO(int id, String firstname, String lastname, int age, String gender, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ReservationDAO> getReservationsOfPassengers() {
        return reservationsOfPassengers;
    }

    public void setReservationsOfPassengers(List<ReservationDAO> reservationsOfPassengers) {
        this.reservationsOfPassengers = reservationsOfPassengers;
    }
}
