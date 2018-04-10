package edu.sjsu.cmpe275.lab2.controllers;

import edu.sjsu.cmpe275.lab2.DTO.BadRequestDTO;
import edu.sjsu.cmpe275.lab2.DTO.ResponseDTO;

public class BaseController {

    public static BadRequestDTO formBadRequest(final String code, final String msg){
        BadRequestDTO badRequestDTO = new BadRequestDTO();
        badRequestDTO.setCode(code);
        badRequestDTO.setMsg(msg);
        return badRequestDTO;
    }

    public static ResponseDTO formSuccessResponse(final String code, final String msg){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMsg(msg);
        return responseDTO;
    }
}
