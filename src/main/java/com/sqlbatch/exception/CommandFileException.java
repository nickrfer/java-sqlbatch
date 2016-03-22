package com.sqlbatch.exception;

public class CommandFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 417567242611841679L;
	
	public CommandFileException(String message) {
		super(message);
	}
	
	public CommandFileException(String message, Exception e) {
		super(message, e);
	}
	
	public CommandFileException(Exception e) {
		super(e);
	}
	
}
