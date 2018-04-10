package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DTO.PassengerDTO;
import edu.sjsu.cmpe275.lab2.DTO.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.sjsu.cmpe275.lab2.services.PassengerService;

import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/passenger")
public class PassengerController {

	private PassengerService passengerService;

	@Autowired
	PassengerController(PassengerService passengerService) {
		this.passengerService = passengerService;
	}

	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPassenger(@PathVariable String id,
										  @RequestParam(value="xml", required=false) String isXML ) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		if(isXML == null) {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		} else if(isXML.equals("true")){
			httpHeaders.setContentType(MediaType.APPLICATION_XML);
		} else {
			httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		}
		
		PassengerDTO passengerDTO = passengerService.getPassenger(Integer.parseInt(id));
		if (passengerDTO != null){
	        return new ResponseEntity<PassengerDTO>(passengerDTO, httpHeaders, HttpStatus.OK);
	    }
	    else{
			BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
					"Sorry, the requested passenger with id "+ id + " does not exist");
			return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
	    }
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createPassenger(@RequestParam(value="firstname") String firstname,
											 @RequestParam(value="lastname") String lastname,
											 @RequestParam(value="age") String age,
											 @RequestParam(value="gender") String gender,
											 @RequestParam(value="phone") String phone) {

		PassengerDTO passengerDTO = passengerService.createPassenger(firstname,
				 lastname, Integer.parseInt(age),gender, phone);
		if (passengerDTO != null){
			return new ResponseEntity<PassengerDTO>(passengerDTO, HttpStatus.OK);
		}
		else{
			BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
					"Another passenger with the same number already exists");
			return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updatePassenger(@PathVariable String id,
											 @RequestParam(value="firstname") String firstname,
											 @RequestParam(value="lastname") String lastname,
											 @RequestParam(value="age") String age,
											 @RequestParam(value="gender") String gender,
											 @RequestParam(value="phone") String phone) {

		PassengerDTO passenger = passengerService.getPassenger(Integer.parseInt(id));

		if(passenger != null){
			PassengerDTO passengerDTO = passengerService.updatePassenger( Integer.parseInt(id),
					firstname, lastname, Integer.parseInt(age),gender, phone);
			if(passengerDTO != null){
				return new ResponseEntity<PassengerDTO>(passengerDTO, HttpStatus.OK);
			} else{
				BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
						"Passenger with phone "+ phone +" already exists");
				return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
			}
		} else{
			BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
					"No passenger with id "+ id +" exists");
			return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deletePassenger(@PathVariable String id) {

		PassengerDTO passenger = passengerService.getPassenger(Integer.parseInt(id));
		if(passenger != null){
			passengerService.deletePassenger(Integer.parseInt(id));
			ResponseDTO response = BaseController.formSuccessResponse("200", "Passenger with id " + id +" is deleted successfully");
			HttpHeaders responseHeader = new HttpHeaders();
			responseHeader.setContentType(MediaType.APPLICATION_XML);
			return new ResponseEntity<>(response,responseHeader
					, HttpStatus.OK);
		} else{
			BadRequestDTO badRequestDTO = BaseController.formBadRequest("404",
					"Passenger with id " + id + " does not exist");
			return new ResponseEntity<>(badRequestDTO, HttpStatus.NOT_FOUND);
		}
	}

}
