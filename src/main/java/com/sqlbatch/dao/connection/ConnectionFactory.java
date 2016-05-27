package com.sqlbatch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL_CONEXAO = "jdbc:sqlserver://%s:%s;DatabaseName=%s";
	private static Connection conx;
	private String serverName;
	private String serverPort;
	private String nomeBD;
	private String dbUser;
	private String dbPassword;

	public ConnectionFactory(String serverName, String serverPort, String nomeBD, String dbUser,
			String dbPassword) {
		this.serverName = serverName;
		this.serverPort = serverPort;
		this.nomeBD = nomeBD;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (conx == null) {
			Class.forName(DRIVER_CLASS);
			String urlConx = prepareConnectionUrl();
			conx = DriverManager.getConnection(urlConx, this.dbUser, this.dbPassword);
		}

		return conx;
	}
	
	public void clearConnection() {
		conx = null;
	}

	private String prepareConnectionUrl() throws SQLException {
		return String.format(URL_CONEXAO, serverName, serverPort, nomeBD);
	}
}