package com.sqlbatch.enums;

public enum DatabaseEnum {

	DB2("DB2"),
	ORACLE("Oracle"),
	SQLSERVER("SQL Server");
	
	private DatabaseEnum(String description) {
		this.description = description;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.description;
	}
}
