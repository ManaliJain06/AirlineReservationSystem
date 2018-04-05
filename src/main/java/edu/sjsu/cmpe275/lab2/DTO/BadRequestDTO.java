package edu.sjsu.cmpe275.lab2.DTO;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@JsonTypeName("BadRequest")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@XmlRootElement(name = "BadRequest")
@XmlType(propOrder = {"code", "msg"})
public class BadRequestDTO {
	
	private String code;
	private String msg;
	
	public BadRequestDTO() {}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {

		this.msg = msg;
	}
}
