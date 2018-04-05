package edu.sjsu.cmpe275.lab2.DTO;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
public class ResponseDTO {
    private String code;
    private String msg;

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
