package com.sqlbatch.dao.connection;

public class SQLServerConnection extends BaseConnectionProvider {
	private static final String DRIVER_CLASS = "net.sourceforge.jtds.jdbc.Driver";
	private static final String CONNECTION_URL = "jdbc:jtds:sqlserver://%s:%s;instance=%s";

	@Override
	public String getDriverClass() {
		return DRIVER_CLASS;
	}

	@Override
	public String getConnectionURL() {
		return CONNECTION_URL;
	}
	

}
