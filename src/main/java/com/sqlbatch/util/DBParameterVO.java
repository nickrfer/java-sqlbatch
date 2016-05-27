package com.sqlbatch.util;

import com.sqlbatch.enums.DatabaseEnum;

public class DBParameterVO {
	
	private String dbName;
	private String serverName;
	private String dbPort;
	private String dbUser;
	private String dbPassword;
	private DatabaseEnum databaseEnum;

	public void setServerName(String nomeServidor) {
		this.serverName = nomeServidor;
	}

	public String getDBName() {
		return this.dbName;
	}

	public void setDBName(String nomeBd) {
		this.dbName = nomeBd;
	}

	public void setDBPort(String portaBd) {
		this.dbPort = portaBd;
	}

	public String getDBUser() {
		return this.dbUser;
	}

	public void setDBUser(String usrBd) {
		this.dbUser = usrBd;
	}

	public String getDBPassword() {
		return this.dbPassword;
	}

	public void setDBPassword(String pswBd) {
		this.dbPassword = pswBd;
	}

	public String getServerName() {
		return this.serverName;
	}

	public String getDBPort() {
		return this.dbPort;
	}
	
	public DatabaseEnum getDatabaseEnum() {
		return databaseEnum;
	}

	public void setDatabaseEnum(DatabaseEnum databaseEnum) {
		this.databaseEnum = databaseEnum;
	}
}