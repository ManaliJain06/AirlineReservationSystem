package edu.sjsu.cmpe275.lab2.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonTypeName("passenger")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@XmlRootElement(name = "passenger") 
@XmlType(propOrder = {"id", "firstname", "lastname", "age", "gender", "phone", "reservations"})
public class PassengerDTO {

	private String id;
	private String firstname;
	private String lastname;
	private String age;
	private String gender;
	private String phone;
	private Reservations reservations;

	public PassengerDTO() {}

	public PassengerDTO(String id, String firstname, String lastname, String age, String gender, String phone,
                        Reservations reservations) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		this.reservations = reservations;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
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

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Reservations getReservations() {
		return reservations;
	}

	public void setReservations(Reservations reservations) {
		this.reservations = reservations;
	};
	
	
}