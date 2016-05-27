package com.sqlbatch.dao.connection;

public class DB2Connection extends BaseConnectionProvider {
	private static final String DRIVER_CLASS = "com.ibm.db2.jcc.DB2Driver";
	private static final String CONNECTION_URL = "jdbc:db2://%s:%s/%s";

	@Override
	public String getDriverClass() {
		return DRIVER_CLASS;
	}

	@Override
	public String getConnectionURL() {
		return CONNECTION_URL;
	}
	

}
