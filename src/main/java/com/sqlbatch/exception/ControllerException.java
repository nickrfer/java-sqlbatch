package com.sqlbatch.exception;

public class ControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 417567242611841679L;
	
	public ControllerException(String message) {
		super(message);
	}
	
	public ControllerException(String message, Exception e) {
		super(message, e);
	}
	
	public ControllerException(Exception e) {
		super(e);
	}
	
}
