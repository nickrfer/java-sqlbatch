package com.sqlbatch.util;

public class DBParameter {
	
	private String dbName;
	private String serverName;
	private String dbPort;
	private String dbUser;
	private String dbPassowrd;

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
		return this.dbPassowrd;
	}

	public void setDBPassword(String pswBd) {
		this.dbPassowrd = pswBd;
	}

	public String getServerName() {
		return this.serverName;
	}

	public String getDBPort() {
		return this.dbPort;
	}
}