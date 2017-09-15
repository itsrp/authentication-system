package com.rp.authenticationsystem.response;

public class Response {

	private Integer code;
	
	private String description;
	
	private Object data;
	
	public Response() {
		// TODO Auto-generated constructor stub
	}
	
	public Response(Integer code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public Response(Integer code, String description, Object data) {
		super();
		this.code = code;
		this.description = description;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
