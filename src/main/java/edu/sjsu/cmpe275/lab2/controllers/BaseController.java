package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DTO.ResponseDTO;

/**
 * A common Util class for the forming requests to be send in the response
 */
public class BaseController {

    /**
     * Method to form bad request
     * @param code
     * @param msg
     * @return BadRequestDTO object
     */
    public static BadRequestDTO formBadRequest(final String code, final String msg){
        BadRequestDTO badRequestDTO = new BadRequestDTO();
        badRequestDTO.setCode(code);
        badRequestDTO.setMsg(msg);
        return badRequestDTO;
    }

    /**
     * Method to form Success Response
     * @param code
     * @param msg
     * @return ResponseDTO object
     */
    public static ResponseDTO formSuccessResponse(final String code, final String msg){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMsg(msg);
        return responseDTO;
    }
}
