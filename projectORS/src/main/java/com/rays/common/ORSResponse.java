package com.rays.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains REST response
 * 
 * @authorHarsh Patidar
 * 
 *
 */
public class ORSResponse {

	public static String DATA = "data";//addData(object value) 
	public static String INPUT_ERROR = "inputerror";//addIputErrors(object value)
	public static String MESSAGE = "message";//addMessage(object value)

	private boolean success = false;//setter-isSuccess(getsuccess)

	private Map<String, Object> result = new HashMap<String, Object>();//get-setter//addResult(string key,object value)

	private String jwttoken;//constructor-getToken

	


	
	public ORSResponse() {
	}

	//jwttoken
	public ORSResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	/*
	 * public String getToken() { return this.jwttoken; }
	 */
	
	
	public ORSResponse(boolean success) {
		this.success = success;
	}

	public ORSResponse(boolean success, String message) {
		this.success = success;
		addMessage(message);
	}

	public ORSResponse(boolean success, String message, Object value) {
		this.success = success;
		addMessage(message);
		addData(value);
		System.out.println("Inside ORSResponse.....");
	}

	
	
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	
	
	
	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	
	
	
	public void addResult(String key, Object value) {
		result.put(key, value);
	}

	public void addData(Object value) {
		result.put(DATA, value);
	}

	public void addInputErrors(Object value) {
		result.put(INPUT_ERROR, value);
	}

	public void addMessage(Object value) {
		result.put(MESSAGE, value);
	}

}
